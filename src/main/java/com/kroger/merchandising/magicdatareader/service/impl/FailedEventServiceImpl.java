package com.kroger.merchandising.magicdatareader.service.impl;

import com.kroger.merchandising.magicdatareader.entity.StorePriceFailedEvent;
import com.kroger.merchandising.magicdatareader.service.FailedEventPersistenceService;
import com.kroger.merchandising.magicdatareader.service.FailedEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FailedEventServiceImpl implements FailedEventService {
    private final FailedEventPersistenceService failedEventPersistenceService;

    @Value("${app.magic.file-error}")
    private String magicFileError;


    private List<StorePriceFailedEvent> failedEvents;

    @Override
    public void addFailedEvent(List<StorePriceFailedEvent> failedEvents) {
        this.failedEvents.addAll(failedEvents);
    }

    @Override
    public void persistFailedEventsIfExist() {
        if(!failedEvents.isEmpty()){
            failedEventPersistenceService.saveAll(this.failedEvents);
            this.failedEvents.clear();
        }
    }



}
