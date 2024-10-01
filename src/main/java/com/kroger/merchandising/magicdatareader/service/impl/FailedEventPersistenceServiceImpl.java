package com.kroger.merchandising.magicdatareader.service.impl;

import com.kroger.merchandising.magicdatareader.entity.StorePriceFailedEvent;
import com.kroger.merchandising.magicdatareader.service.FailedEventPersistenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FailedEventPersistenceServiceImpl implements FailedEventPersistenceService {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void saveAll(List<StorePriceFailedEvent> failedEvents) {
        String sql = "INSERT INTO store_price_delivery.store_price_events " +
                "(created_timestamp, division_id, event_message, is_event_published, updated_timestamp, event_id) VALUES " +
                "(?, ?, ?, ?, ?, ?)";

        try {
            jdbcTemplate.batchUpdate(sql, new PersistFailedEventsPreparedStatementSetter(failedEvents));
        } catch (Exception ex) {
            log.error("Error while inserting failed events into store_price_events table: {}", ex.getMessage());
        }
    }
}
