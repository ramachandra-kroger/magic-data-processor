package com.kroger.merchandising.magicdatareader.batch.listener;


import com.kroger.merchandising.magicdatareader.service.FailedEventPersistenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemProcessListener;

@Slf4j
@RequiredArgsConstructor
public class CustomItemProcesorListener<T,S> implements ItemProcessListener<T,S> {
    private final FailedEventPersistenceService failedEventPersistenceService;

    @Override
    public void onProcessError(T item, Exception ex) {
        log.error("Error while parsing magi-data from file: {}", ex.getMessage(), ex);
        failedEventPersistenceService.saveBadFileText(item.toString(), "division","jobID");
    }
}
