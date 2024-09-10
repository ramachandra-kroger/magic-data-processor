package com.kroger.merchandising.magicdatareader.batch.processor;


import com.kroger.merchandising.magicdatareader.domain.DataItem;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class DataItemProcessor implements ItemProcessor<DataItem, DataItem> {
    @Override
    public DataItem process(DataItem item) throws Exception {
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
        return item;
    }
}
