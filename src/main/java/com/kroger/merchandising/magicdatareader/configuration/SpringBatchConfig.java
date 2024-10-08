package com.kroger.merchandising.magicdatareader.configuration;

import com.kroger.desp.events.merchandising.storeprice.StorePriceUpdateEvent;
import com.kroger.merchandising.magicdatareader.batch.processor.DataItemProcessor;
import com.kroger.merchandising.magicdatareader.batch.reader.DataItemReader;
import com.kroger.merchandising.magicdatareader.batch.writer.CustomKafkaItemWriter;
import com.kroger.merchandising.magicdatareader.domain.DataItem;
import com.kroger.merchandising.magicdatareader.listener.CustomChunkListener;
import com.kroger.merchandising.magicdatareader.listener.CustomItemProcesorListener;
import com.kroger.merchandising.magicdatareader.listener.CustomJobListener;
import com.kroger.merchandising.magicdatareader.listener.CustomStepExecutionListener;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;



@Configuration
@RequiredArgsConstructor
public class SpringBatchConfig {

    private final JobRepository jobRepository;
    private final DataItemReader dataItemReader;
    private final CustomKafkaItemWriter kafkaItemWriter;
    private final DataItemProcessor dataItemProcessor;

    @Value("${spring.batch.chunk-size}")
    private int chunkSize;


    @Bean(name = "simpleAsyncTaskExecutor")
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor simpleAsyncTaskExecutor =  new SimpleAsyncTaskExecutor("spring_batch");
                simpleAsyncTaskExecutor.setConcurrencyLimit(5);
                return simpleAsyncTaskExecutor;
    }


    @Bean(name = "insertIntoDbFromFileJob")
    public Job insertIntoDbFromFileJob(Step stepPublisher) {
        var name = "MagicDataReaderJob";
        var builder = new JobBuilder(name, jobRepository);
        return builder.start(stepPublisher).listener(new CustomJobListener()).build();
    }

    @Bean
    public Step stepPublisher(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager){
        return new StepBuilder("magicDataPublisher", jobRepository)
                .<DataItem, StorePriceUpdateEvent>chunk(chunkSize, platformTransactionManager)
                .reader(dataItemReader)
                .processor(dataItemProcessor)
                .writer(kafkaItemWriter)
                .listener(new CustomChunkListener())
                .listener(new CustomStepExecutionListener())
                .listener(new CustomItemProcesorListener<>())
                .faultTolerant()
                .skipLimit(20)
                .skip(FlatFileParseException.class)
                .taskExecutor(taskExecutor())
                .build();
    }





}
