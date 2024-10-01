package com.kroger.merchandising.magicdatareader.service.impl;

import com.kroger.desp.events.merchandising.storeprice.StorePriceUpdateEvent;
import com.kroger.merchandising.magicdatareader.configuration.exception.MagicDataReaderException;
import com.kroger.merchandising.magicdatareader.entity.StorePriceFailedEvent;
import com.kroger.merchandising.magicdatareader.service.FailedEventService;
import com.kroger.merchandising.magicdatareader.service.KafkaClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaClientServiceImpl implements KafkaClientService {
    private final KafkaTemplate<String, SpecificRecord> kafkaTemplate;
    private final FailedEventService failedEventService;

    @Value("${app.target-topic}")
    private String targetTopic;

    @Override
    public void sendKafkaEvent(String itemUpc, String division, StorePriceUpdateEvent eventMessage) throws MagicDataReaderException {
        ProducerRecord<String, SpecificRecord> producerRecord = new ProducerRecord<>(targetTopic, itemUpc, eventMessage);
        try {
            CompletableFuture<SendResult<String, SpecificRecord>> future = kafkaTemplate.send(producerRecord);
            future.whenComplete((result, ex) -> {
                if (!ObjectUtils.isEmpty(ex)) {
                    log.error("Error while publishing message: {}, Exception Message: {}", eventMessage, ex.getMessage());
                    StorePriceFailedEvent storePriceFailedEvent = new StorePriceFailedEvent(UUID.fromString(eventMessage.getEventHeader().getId()), eventMessage.toString().getBytes(),false, division,  LocalDateTime.now(), LocalDateTime.now());
                    failedEventService.handleFailedToPublishEvent(storePriceFailedEvent);
                }
            });
        } catch (Exception ex) {
            log.error("Error while sending event to DESP: {}", ex.getMessage());
            throw new MagicDataReaderException("Error while publishing event: " + ex.getMessage(), ex);
        }
    }
}
