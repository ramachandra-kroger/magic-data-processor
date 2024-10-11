package com.kroger.merchandising.magicdatareader.batch.listener;


import com.kroger.merchandising.magicdatareader.service.FailedEventPersistenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
@RequiredArgsConstructor
public class CustomItemProcesorListener<T,S> implements ItemProcessListener<T,S> {
    private final FailedEventPersistenceService failedEventPersistenceService;

    @Value("#{stepExecution}")
    private StepExecution stepExecution;

    @Override
    public void onProcessError(T item, Exception ex) {
        log.error("Error while parsing magi-data from file: {}", ex.getMessage(), ex);
        failedEventPersistenceService.saveBadFileText(item.toString(), "999", stepExecution.getJobExecutionId());
    }
}
