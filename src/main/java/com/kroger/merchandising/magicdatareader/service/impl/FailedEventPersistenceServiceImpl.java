package com.kroger.merchandising.magicdatareader.service.impl;

import com.kroger.merchandising.magicdatareader.entity.StorePriceFailedEvent;
import com.kroger.merchandising.magicdatareader.service.FailedEventPersistenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FailedEventPersistenceServiceImpl implements FailedEventPersistenceService {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void saveAll(List<StorePriceFailedEvent> failedEvents) {
        String sql = "INSERT INTO store_price.store_price_events " +
                "(created_timestamp, division_id, event_message, is_event_published, updated_timestamp, event_id) VALUES " +
                "(?, ?, ?, ?, ?, ?)";

        try {
            jdbcTemplate.batchUpdate(sql, new PersistFailedEventsPreparedStatementSetter(failedEvents));
        } catch (Exception ex) {
            log.error("Error while inserting failed events into store_price_events table: {}", ex.getMessage());
        }
    }

    @Override
    public void saveBadFileText(String badFileText, String division, String jobId) {
        String sql = "INSERT INTO store_price.magic_file_bad_data (created_timestamp, division_id, text_line, job_id) values (?,?,?,?);";
        PreparedStatementSetter preparedStatementSetter = ps -> {
            ps.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(2, division);
            ps.setString(3, badFileText);
            ps.setString(4, jobId);
        };
        try {
            jdbcTemplate.update(sql, preparedStatementSetter);
        } catch (Exception ex) {
            log.error("Error while inserting magic_file_bad_data into store_price table: {}", ex.getMessage());
        }
    }


}
