package com.kroger.merchandising.magicdatareader.listener;

import com.kroger.merchandising.magicdatareader.service.FailedEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.item.file.FlatFileParseException;


@Slf4j
@RequiredArgsConstructor
public class CustomItemReadListener<T> implements ItemReadListener<T> {
    private final FailedEventService failedEventService;

    @Override
    public void onReadError(Exception ex) {
        log.error("Unable to read/map input: {}", ex.getMessage());
        if(ex instanceof FlatFileParseException ffe){
            failedEventService.handleBadRecord(ffe.getInput());
        }
    }
}
