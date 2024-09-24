package com.kroger.merchandising.magicdatareader.configuration;

import com.kroger.desp.events.merchandising.storeprice.StorePriceUpdateEvent;
import com.kroger.merchandising.magicdatareader.batch.processor.DataItemProcessor;
import com.kroger.merchandising.magicdatareader.batch.reader.DataItemReader;
import com.kroger.merchandising.magicdatareader.batch.writer.CustomKafkaItemWriter;
import com.kroger.merchandising.magicdatareader.domain.DataItem;
import com.kroger.merchandising.magicdatareader.listener.CustomChunkListener;
import com.kroger.merchandising.magicdatareader.listener.CustomJobListener;
import com.kroger.merchandising.magicdatareader.listener.CustomStepExecutionListener;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
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
    public Job insertIntoDbFromFileJob(Step step1) {
        var name = "MagicDataReaderJob";
        var builder = new JobBuilder(name, jobRepository);
        return builder.start(step1).listener(new CustomJobListener()).build();
    }

    @Bean
    public Step step1(@Qualifier("simpleAsyncTaskExecutor") TaskExecutor taskExecutor, JobRepository jobRepository, PlatformTransactionManager platformTransactionManager){
        var name = "MagicDataWriterStep";
        var builder = new StepBuilder(name, jobRepository);
        return builder.<DataItem, StorePriceUpdateEvent>chunk(chunkSize, platformTransactionManager)
                .reader(dataItemReader)
                .processor(dataItemProcessor)
                .writer(kafkaItemWriter)
                .listener(new CustomChunkListener())
//                .listener(new CustomItemProcesorListener<>())
//                .listener(new CustomItemReadListener<>())
                .listener(new CustomStepExecutionListener())
                .taskExecutor(taskExecutor())
                .build();
    }



}
