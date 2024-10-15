package com.kroger.merchandising.magicdatareader.service;

import com.kroger.merchandising.magicdatareader.domain.StorePriceFailedEvent;

import java.util.List;

public interface FailedEventPersistenceService {
    void saveAll(List<StorePriceFailedEvent> failedEvents);
    void saveBadFileText(String badFileText, String division, Long jobId);
}
