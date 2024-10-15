package com.kroger.merchandising.magicdatareader.service;



import com.kroger.merchandising.magicdatareader.domain.StorePriceFailedEvent;

import java.util.List;

public interface FailedEventService {
    void addFailedEvent(List<StorePriceFailedEvent> failedEvents);
    void persistFailedEventsIfExist();

}
