package com.kroger.merchandising.magicdatareader.batch.writer;

import com.kroger.desp.events.merchandising.storeprice.StorePriceUpdateEvent;
import com.kroger.merchandising.magicdatareader.configuration.exception.MagicDataReaderException;
import com.kroger.merchandising.magicdatareader.service.KafkaClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomKafkaItemWriter implements ItemWriter<StorePriceUpdateEvent> {

    private final KafkaClientService kafkaClientService;

    @Override
    public void write(Chunk<? extends StorePriceUpdateEvent> chunk) throws MagicDataReaderException {

        for (StorePriceUpdateEvent event : chunk) {
            try {
                kafkaClientService.sendKafkaEvent(event.getUpc(), event.getDivision(), event);
            } catch (MagicDataReaderException e) {
                throw new MagicDataReaderException("Error while publishing event: " + event.getUpc(), e);
            }
        }
    }


}
