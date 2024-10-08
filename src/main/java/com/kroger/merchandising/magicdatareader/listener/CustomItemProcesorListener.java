package com.kroger.merchandising.magicdatareader.listener;


import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemProcessListener;

@Slf4j
public class CustomItemProcesorListener<T,S> implements ItemProcessListener<T,S> {

    @Override
    public void onProcessError(T item, Exception ex) {
        log.error("Error during item {} proces: {}", item, ex.getMessage());
    }
}
