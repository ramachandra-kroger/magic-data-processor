package com.kroger.merchandising.magicdatareader.batch.writer;

import com.kroger.desp.events.merchandising.storeprice.StorePriceUpdateEvent;
import com.kroger.merchandising.magicdatareader.configuration.exception.MagicDataReaderException;
import com.kroger.merchandising.magicdatareader.configuration.exception.MagicRunTimeException;
import com.kroger.merchandising.magicdatareader.domain.StorePriceFailedEvent;
import com.kroger.merchandising.magicdatareader.service.FailedEventPersistenceService;
import com.kroger.merchandising.magicdatareader.service.KafkaClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomKafkaItemWriter implements ItemWriter<StorePriceUpdateEvent> {

    private final KafkaClientService kafkaClientService;
    private final FailedEventPersistenceService failedEventPersistenceService;
    private List<StorePriceFailedEvent> failedEvents = new ArrayList<>();

    @Override
    public void write(Chunk<? extends StorePriceUpdateEvent> chunk) {
        chunk.forEach(event-> {
            try {
                kafkaClientService.sendKafkaEvent(event.getUpc(), event.getDivision(), event);
            } catch (Exception ex) {
                log.error("Failed to send event {}", ex.getMessage());
                LocalDateTime locateDateTime = LocalDateTime.now();
                failedEvents.add(new StorePriceFailedEvent(UUID.fromString(event.getEventHeader().getId()),
                        event.toString().getBytes(),
                        Boolean.FALSE,
                        event.getDivision(),
                        locateDateTime,
                        locateDateTime
                ));

            }
        });

        if(!failedEvents.isEmpty()){
            failedEventPersistenceService.saveAll(failedEvents);
            failedEvents.clear();
        }

    }


}
