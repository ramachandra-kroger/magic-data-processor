package com.kroger.merchandising.magicdatareader.configuration;

import com.kroger.desp.events.merchandising.storeprice.StorePriceUpdateEvent;
import com.kroger.merchandising.magicdatareader.batch.policy.CustomSkipPolicy;
import com.kroger.merchandising.magicdatareader.batch.processor.DataItemProcessor;
import com.kroger.merchandising.magicdatareader.batch.reader.DataItemReader;
import com.kroger.merchandising.magicdatareader.batch.writer.CustomKafkaItemWriter;
import com.kroger.merchandising.magicdatareader.domain.DataItem;
import com.kroger.merchandising.magicdatareader.batch.listener.CustomItemProcesorListener;
import com.kroger.merchandising.magicdatareader.batch.listener.CustomJobListener;
import com.kroger.merchandising.magicdatareader.batch.listener.CustomReadListener;
import com.kroger.merchandising.magicdatareader.batch.listener.CustomStepExecutionListener;
import com.kroger.merchandising.magicdatareader.service.FailedEventPersistenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;


@Configuration
@RequiredArgsConstructor
public class SpringBatchConfig {

    private final JobRepository jobRepository;
    private final DataItemReader dataItemReader;
    private final CustomKafkaItemWriter kafkaItemWriter;
    private final DataItemProcessor dataItemProcessor;
    private final FailedEventPersistenceService failedEventPersistenceService;

    @Value("${spring.batch.chunk-size}")
    private int chunkSize;

    @Bean
    public SkipPolicy skipPolicy() {
        return new CustomSkipPolicy();
    }

    @Bean(name = "simpleAsyncTaskExecutor")
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(4); // Set the number of threads to be used for parallel processing
        taskExecutor.setMaxPoolSize(8); // Set the maximum number of threads
        taskExecutor.setThreadNamePrefix("batch-thread-");
        taskExecutor.initialize();
        return taskExecutor;
    }

    @Bean(name = "insertIntoDbFromFileJob")
    public Job insertIntoDbFromFileJob(Step stepPublisher) {
        var name = "MagicDataReaderJob";
        var builder = new JobBuilder(name, jobRepository);
        return builder.start(stepPublisher).listener(new CustomJobListener()).build();
    }

    @Bean
    public Step stepPublisher(JobRepository jobRepository, @Qualifier(value = "magicTransactionManager") PlatformTransactionManager transactionManager){
        return new StepBuilder("magicDataPublisher", jobRepository)
                .<DataItem, StorePriceUpdateEvent>chunk(chunkSize, transactionManager)
                .reader(dataItemReader)
                .processor(dataItemProcessor)
                .writer(kafkaItemWriter)
                .listener(new CustomStepExecutionListener())
                .listener(customReadListener())
                .listener(customItemProcesorListener())
                .faultTolerant()
                .skipPolicy(skipPolicy())
                .taskExecutor(taskExecutor())
                .build();
    }


    @Bean
    @StepScope
    public CustomReadListener customReadListener() {
        return new CustomReadListener(failedEventPersistenceService);
    }

    @Bean
    @StepScope
    public CustomItemProcesorListener<StorePriceUpdateEvent, Throwable> customItemProcesorListener() {
        return new CustomItemProcesorListener<>(failedEventPersistenceService);
    }



}
