package com.kroger.merchandising.magicdatareader.batch.reader;


import com.kroger.merchandising.magicdatareader.configuration.exception.MagicDataReaderException;
import com.kroger.merchandising.magicdatareader.domain.DataItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class DataItemReader extends FlatFileItemReader<DataItem> {

    public DataItemReader() {
        super();
        log.info("Initializing DataItemReader");
        setResource(new ClassPathResource("/data/magic-data.txt"));
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter("|");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("locationNumber", "upc", "quantitie1", "permanentPrice", "quantitie2", "teporaryPrice", "effecctiveDateFrom","effecctiveDateTo","timingFlag","durationFlag","sku","magicCoupon", "couponUpc");
        BeanWrapperFieldSetMapper<DataItem> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(DataItem.class);
        DefaultLineMapper<DataItem> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        setLineMapper(lineMapper);
    }

}
