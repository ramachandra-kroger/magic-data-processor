package com.kroger.merchandising.magicdatareader.service;

import org.apache.avro.specific.SpecificRecord;

public interface KafkaClientService {
    void sendKafkaEvent(String itemUpc, String division, String eventId, SpecificRecord eventMessage);
}
