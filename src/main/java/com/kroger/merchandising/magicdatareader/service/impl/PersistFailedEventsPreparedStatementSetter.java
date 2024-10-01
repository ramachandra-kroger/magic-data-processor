package com.kroger.merchandising.magicdatareader.service.impl;


import com.kroger.merchandising.magicdatareader.entity.StorePriceFailedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class PersistFailedEventsPreparedStatementSetter implements BatchPreparedStatementSetter {

    private final List<StorePriceFailedEvent> storePriceFailedEvents;

    @Override
    public void setValues(PreparedStatement ps, int i) throws SQLException {
        StorePriceFailedEvent storePriceFailedEvent = storePriceFailedEvents.get(i);

        ps.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(2, storePriceFailedEvent.getDivisionId());
        ps.setBytes(3, storePriceFailedEvent.getEventMessage());
        ps.setBoolean(4, false);
        ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
        ps.setObject(6, storePriceFailedEvent.getEventId());
    }

    @Override
    public int getBatchSize() {
        return storePriceFailedEvents.size();
    }

}
