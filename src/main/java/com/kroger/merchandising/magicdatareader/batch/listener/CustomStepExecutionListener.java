package com.kroger.merchandising.magicdatareader.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

@Slf4j
public class CustomStepExecutionListener implements StepExecutionListener {

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        if (stepExecution.getReadCount() > 0){
            log.info("Total amount of records published: {}", stepExecution.getReadCount());
            return stepExecution.getExitStatus();
        }
        else return ExitStatus.FAILED;
    }

}
