package com.kroger.merchandising.magicdatareader.batch.listener;

import com.kroger.desp.events.merchandising.storeprice.StorePriceUpdateEvent;
import com.kroger.merchandising.magicdatareader.entity.StorePriceFailedEvent;
import com.kroger.merchandising.magicdatareader.service.FailedEventPersistenceService;
import com.kroger.merchandising.magicdatareader.service.FailedEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.item.Chunk;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class CustomItemWriterListener implements ItemWriteListener<StorePriceUpdateEvent> {

    private final FailedEventPersistenceService failedEventPersistenceService;

    @Override
    public void onWriteError(Exception exception, Chunk<? extends StorePriceUpdateEvent> failedItems){
        log.error("Error while trying to publish {} events", failedItems.size(), exception);
        LocalDateTime locateDateTime = LocalDateTime.now(ZoneId.of("America/New_York"));
        List<StorePriceFailedEvent> storePriceFailedEvents = failedItems.getItems()
                .stream()
                .map(item -> new StorePriceFailedEvent(UUID.fromString(item.getEventHeader().getId()),item.toString().getBytes(),Boolean.FALSE, item.getDivision(),locateDateTime, locateDateTime))
                .toList();
        failedEventPersistenceService.saveAll(storePriceFailedEvents);
        log.info("{} were stored in Database..", failedItems.size());

    }
}
