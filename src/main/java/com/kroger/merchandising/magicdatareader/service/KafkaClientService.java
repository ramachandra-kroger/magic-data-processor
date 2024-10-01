package com.kroger.merchandising.magicdatareader.service;

import com.kroger.desp.events.merchandising.storeprice.StorePriceUpdateEvent;
import com.kroger.merchandising.magicdatareader.configuration.exception.MagicDataReaderException;
import org.apache.avro.specific.SpecificRecord;

public interface KafkaClientService {
    void sendKafkaEvent(String itemUpc, String division, StorePriceUpdateEvent eventMessage) throws MagicDataReaderException;
}
