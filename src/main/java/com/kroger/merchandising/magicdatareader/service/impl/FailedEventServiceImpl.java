package com.kroger.merchandising.magicdatareader.service.impl;

import com.kroger.merchandising.magicdatareader.service.FailedEventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@Service
@Slf4j
public class FailedEventServiceImpl implements FailedEventService {
    @Value("${app.magic.file-error}")
    private String magicFileError;

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
}
