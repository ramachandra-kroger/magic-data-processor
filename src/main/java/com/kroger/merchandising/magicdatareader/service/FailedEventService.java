package com.kroger.merchandising.magicdatareader.service;



import com.kroger.merchandising.magicdatareader.entity.StorePriceFailedEvent;

public interface FailedEventService {
    void handleBadRecord(String event);
    void handleFailedToPublishEvent(StorePriceFailedEvent  storePriceFailedEvent);
    void persistFailedEventsIfExist();
}
