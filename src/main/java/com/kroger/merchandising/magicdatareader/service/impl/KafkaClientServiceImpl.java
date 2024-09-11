package com.kroger.merchandising.magicdatareader.service.impl;

import com.kroger.merchandising.magicdatareader.configuration.exception.MagicDataReaderException;
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

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaClientServiceImpl implements KafkaClientService {
    private final KafkaTemplate<String, SpecificRecord> kafkaTemplate;

    @Value("${app.target-topic}")
    private String targetTopic;

    @Override
    public void sendKafkaEvent(String itemUpc, String division, SpecificRecord eventMessage) throws MagicDataReaderException {
        ProducerRecord<String, SpecificRecord> producerRecord = new ProducerRecord<>(targetTopic, itemUpc, eventMessage);
        try {
            CompletableFuture<SendResult<String, SpecificRecord>> future = kafkaTemplate.send(producerRecord);
            future.whenComplete((result, ex) -> {
                if (ObjectUtils.isEmpty(ex)) {
                    log.debug("Success processing message -  division: {}, itemUpc: {}", division, itemUpc);
                } else {
                    log.error("Error while processing message: {}, Exception Message: {}", eventMessage, ex.getMessage());
                }
            });
        } catch (Exception ex) {
            log.error("Error while sending event to DESP: {}", ex.getMessage());
            throw new MagicDataReaderException("Error while publishing event: " + ex.getMessage(), ex);
        }
    }
}
