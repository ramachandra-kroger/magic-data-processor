package com.kroger.merchandising.magicdatareader.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;

@Slf4j
public class CustomItemReadListener<T> implements ItemReadListener<T> {

//    @Override
//    public void beforeRead() {
//        log.info("Before reading item");
//    }

    @Override
    public void afterRead(T item) {
        log.info("After reading item: {}", item.toString());
    }

    @Override
    public void onReadError(Exception ex) {
        log.error("Error during item reading: {}", ex.getMessage());
    }
}
