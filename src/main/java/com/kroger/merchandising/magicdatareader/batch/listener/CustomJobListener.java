package com.kroger.merchandising.magicdatareader.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

@Slf4j
public class CustomJobListener implements JobExecutionListener {

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("MAGIC DATA JOB FINISHED !!");
        }
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("MAGIC DATA JOB STARTED !!");
    }
}
