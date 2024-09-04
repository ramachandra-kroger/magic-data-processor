package com.kroger.merchandising.magicdatareader.batch.writer;

import com.kroger.merchandising.magicdatareader.entity.DataItem;
import com.kroger.merchandising.magicdatareader.repository.DataItemRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class DataItemWriter implements ItemWriter<DataItem> {

    private final DataItemRepository dataItemRepository;


    @Override
    public void write(@NonNull  Chunk<? extends DataItem> dataItemsChunk) throws Exception {
        log.info("Writer Thread: {}", Thread.currentThread().getName());
        dataItemRepository.saveAll(dataItemsChunk);
        log.info("Writer Thread: {}, chunk of {} saved to database", Thread.currentThread().getName(), dataItemsChunk.size());
    }
}
