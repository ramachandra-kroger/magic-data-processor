package com.kroger.merchandising.magicdatareader.batch.listener;

import com.kroger.desp.events.merchandising.storeprice.StorePriceUpdateEvent;
import com.kroger.merchandising.magicdatareader.domain.DataItem;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.SkipListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomSkipListener<T,S> implements SkipListener<T, S> {

    @Override // item reader
    public void onSkipInRead(Throwable throwable) {
        log.info("A failure on read {} ", throwable.getMessage());
    }

    @Override // item writer
    public void onSkipInWrite(S item, Throwable t) {
        log.info("A failure on write {} , {}", t.getMessage(), item.toString());
    }

    @SneakyThrows
    @Override // item processor
    public void onSkipInProcess(T item, Throwable t) {
        log.info("Item {}  was skipped due to the exception  {}", item.toString(), t.getMessage());
    }
}
