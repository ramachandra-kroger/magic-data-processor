package com.kroger.merchandising.magicdatareader.batch.reader;

import com.kroger.merchandising.magicdatareader.domain.DataItem;
import com.kroger.merchandising.magicdatareader.configuration.exception.MagicDataReaderException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.kroger.merchandising.magicdatareader.utils.FileLoaderUtils.loadFileIfExists;

@Slf4j
@Component
public class DataItemReader extends FlatFileItemReader<DataItem> {

    public DataItemReader(@Value("#{jobParameters[directoryFullPath]}") String directoryFullPath) throws MagicDataReaderException {
        super();
        log.info("Initializing DataItemReader");
        setResource(Objects.requireNonNull(loadFileIfExists(directoryFullPath)));
        setStrict(true);
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
