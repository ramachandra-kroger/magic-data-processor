package com.kroger.merchandising.magicdatareader.service;

import com.kroger.merchandising.magicdatareader.configuration.exception.MagicDataReaderException;
import org.apache.avro.specific.SpecificRecord;

public interface KafkaClientService {
    void sendKafkaEvent(String itemUpc, String division, SpecificRecord eventMessage) throws MagicDataReaderException;
}
