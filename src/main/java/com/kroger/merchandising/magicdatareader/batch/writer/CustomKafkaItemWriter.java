package com.kroger.merchandising.magicdatareader.batch.writer;

import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.kafka.core.KafkaTemplate;

@RequiredArgsConstructor
public class CustomKafkaItemWriter<T> implements ItemWriter<T> {


    @Override
    public void write(Chunk<? extends T> chunk) throws Exception {

    }
}
