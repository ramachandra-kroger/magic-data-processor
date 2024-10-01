package com.kroger.merchandising.magicdatareader.service.impl;

import com.kroger.merchandising.magicdatareader.entity.StorePriceFailedEvent;
import com.kroger.merchandising.magicdatareader.service.FailedEventPersistenceService;
import com.kroger.merchandising.magicdatareader.service.FailedEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
    public void handleBadRecord(String event) {
        log.error("Failed to handle record: {}", event);
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(magicFileError);
        } catch (IOException ex) {
            log.error("Error while trying to open magic failed events file: {}", magicFileError, ex);
        }
        if(!ObjectUtils.isEmpty(fileWriter)) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
                bufferedWriter.write(event);
                bufferedWriter.newLine();
            } catch (Exception e) {
                log.error("Error while trying to write magic failed events to file: {}", magicFileError, e);
            }
        } else {
            log.error("Error while trying to write magic failed events to file: {}", magicFileError);
        }
    }

    @Override
    public void handleFailedToPublishEvent(StorePriceFailedEvent storePriceFailedEvent) {
        this.failedEvents.add(storePriceFailedEvent);
    }

    @Override
    public void persistFailedEventsIfExist() {
        if(!this.failedEvents.isEmpty()){
            failedEventPersistenceService.saveAll(this.failedEvents);
            this.failedEvents.clear();
        }
    }

}
