package com.kroger.merchandising.magicdatareader.batch.writer;

import com.kroger.desp.commons.merchandising.storeprice.EventHeader;
import com.kroger.desp.events.merchandising.storeprice.StorePriceUpdateEvent;
import com.kroger.merchandising.magicdatareader.domain.DataItem;
import com.kroger.merchandising.magicdatareader.service.KafkaClientService;
import com.kroger.merchandising.magicdatareader.service.UUIDGenerator;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.time.Instant;

import static com.kroger.merchandising.magicdatareader.utils.Constants.BASE;
import static com.kroger.merchandising.magicdatareader.utils.Constants.EVENT_SOURCE;
import static com.kroger.merchandising.magicdatareader.utils.Constants.UPSERT_PRICE_INFO;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomKafkaItemWriter implements ItemWriter<DataItem> {

    private final KafkaClientService kafkaClientService;
    private final UUIDGenerator uuidGenerator;


    @Override
    public void write(Chunk<? extends DataItem> chunk) throws Exception {
        chunk.forEach(dataItem -> {
            String eventId = uuidGenerator.generateUUID().toString();
            kafkaClientService.sendKafkaEvent(
                    dataItem.getUpc(),
                    "014",
                    eventId,
                    getStorePriceUpdateEvent(dataItem, eventId)
            );
            log.info("sending item to desp with upc: {}", dataItem.getUpc());
        });
    }

    @NotNull
    private StorePriceUpdateEvent getStorePriceUpdateEvent(DataItem dataItem, String eventId) {
        return new StorePriceUpdateEvent(getEventHeader(eventId), dataItem.getUpc(), "014", dataItem.getLocationNumber(), "09/10/2024", "Unit", "Unit", "L", null, "19.99", "1", "14.99", 1, null, null, null, dataItem.getCouponUpc(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, BASE);
    }



    public EventHeader getEventHeader(String eventId) {
        return EventHeader.newBuilder()
                .setId(eventId)
                .setTime(Instant.now().toEpochMilli())
                .setType(UPSERT_PRICE_INFO)
                .setSource(EVENT_SOURCE).build();
    }
}
