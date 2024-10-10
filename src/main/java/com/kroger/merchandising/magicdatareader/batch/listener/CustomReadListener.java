package com.kroger.merchandising.magicdatareader.batch.listener;

import com.kroger.merchandising.magicdatareader.domain.DataItem;
import com.kroger.merchandising.magicdatareader.service.FailedEventPersistenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.item.file.FlatFileParseException;

@Slf4j
@RequiredArgsConstructor
public class CustomReadListener implements ItemReadListener<DataItem> {

    private final FailedEventPersistenceService failedEventPersistenceService;

    @Override
    public void onReadError(Exception e) {
        if ( e instanceof FlatFileParseException flatFileParseException) {
            log.error("Error while parsing magi-data from file: {}", e.getMessage(), e);
            failedEventPersistenceService.saveBadFileText(flatFileParseException.getInput(), "division","jobID");
        }
        log.error("Error while parsing magi-data from file: {}, not able to read file source", e.getMessage(), e);
    }
}
