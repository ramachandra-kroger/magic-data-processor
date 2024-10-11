package com.kroger.merchandising.magicdatareader.batch.listener;

import com.kroger.merchandising.magicdatareader.domain.DataItem;
import com.kroger.merchandising.magicdatareader.service.FailedEventPersistenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
@RequiredArgsConstructor
public class CustomReadListener implements ItemReadListener<DataItem> {

    private final FailedEventPersistenceService failedEventPersistenceService;

    @Value("#{stepExecution}")
    private StepExecution stepExecution;

    @Override
    public void onReadError( Exception ex) {
        if ( ex instanceof FlatFileParseException flatFileParseException) {
            log.error("Error while parsing magic-data from file: {}", ex.getMessage(), ex);
            failedEventPersistenceService.saveBadFileText(flatFileParseException.getInput(), "999", stepExecution.getJobExecutionId());
        }
        log.error("Error while parsing magic-data from file: {}, not able to read file source", ex.getMessage(), ex);
    }
}
