package com.kroger.merchandising.magicdatareader.batch.processor;


import com.kroger.desp.commons.merchandising.storeprice.EventHeader;
import com.kroger.desp.events.merchandising.storeprice.StorePriceUpdateEvent;
import com.kroger.merchandising.magicdatareader.domain.DataItem;
import com.kroger.merchandising.magicdatareader.service.UUIDGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import java.time.Instant;

import static com.kroger.merchandising.magicdatareader.utils.Constants.BASE;
import static com.kroger.merchandising.magicdatareader.utils.Constants.EVENT_SOURCE;
import static com.kroger.merchandising.magicdatareader.utils.Constants.UPSERT_PRICE_INFO;

@Component
@RequiredArgsConstructor
public class DataItemProcessor implements ItemProcessor<DataItem, StorePriceUpdateEvent> {
    private final UUIDGenerator uuidGenerator;


    @Override
    public StorePriceUpdateEvent process(DataItem item) throws Exception {
        //TODO- apply any logic needed for records fields
        //derivate quantitie1
        if(item.getTemporaryPrice().equals("0000000")){
            item.setQuantitie1("000");
        } else {
            item.setQuantitie1("001");
        }
        //derivate quantitie2
        if(item.getDurationFlag().equals("P")){
            item.setQuantitie2("001");
        } else {
            item.setQuantitie2("000");
        }


        EventHeader eventHeader = EventHeader.newBuilder()
                .setId(uuidGenerator.generateUUID().toString())
                .setTime(Instant.now().toEpochMilli())
                .setType(UPSERT_PRICE_INFO)
                .setSource(EVENT_SOURCE).build();

        return new StorePriceUpdateEvent(eventHeader, item.getUpc(), "014", item.getLocationNumber(), "09/10/2024", "Unit", "Unit", "L", null, "19.99", "1", "14.99", 1, null, null, null, item.getCouponUpc(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, BASE);


    }


}
