package com.kroger.merchandising.magicdatareader.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

@Slf4j
public class CustomStepExecutionListener implements StepExecutionListener {

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        if (stepExecution.getReadCount() > 0){
            log.info("Read count from file is {}", stepExecution.getReadCount());
            return stepExecution.getExitStatus();
        }
        else return ExitStatus.FAILED;
    }
}
