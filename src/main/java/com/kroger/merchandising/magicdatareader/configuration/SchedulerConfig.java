package com.kroger.merchandising.magicdatareader.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SchedulerConfig {
    private final JobLauncher jobLauncher;
    private final Job job;

    @Value("${app.magic-data.directory}")
    String fileFullPath;


    @Scheduled(cron = "0 */5 * * * ?")
    public void runJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startedAt", System.currentTimeMillis())
                .addString("fileFullPath", fileFullPath)
                .toJobParameters();
        jobLauncher.run(job, jobParameters);
    }


}
