package com.kroger.merchandising.magicdatareader.batch.reader;

import com.kroger.merchandising.magicdatareader.configuration.exception.MagicDataReaderException;
import com.kroger.merchandising.magicdatareader.domain.DataItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.stereotype.Component;
import org.springframework.core.io.Resource;

import static com.kroger.merchandising.magicdatareader.utils.FileLoaderUtils.loadFileIfExists;


@Slf4j
@Component
public class DataItemReader extends FlatFileItemReader<DataItem> implements StepExecutionListener {


    public DataItemReader() throws MagicDataReaderException {
        super();
        log.info("Initializing DataItemReader");
        //TODO- move setResource
//        setResource(Objects.requireNonNull(resource));
        setStrict(true);
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter("|");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("location", "upc", "quantitie1", "permanentPrice", "quantitie2", "teporaryPrice", "effecctiveDateFrom","effecctiveDateTo","timingFlag","durationFlag","sku","magicCoupon", "couponUpc");
        BeanWrapperFieldSetMapper<DataItem> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(DataItem.class);
        DefaultLineMapper<DataItem> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        setLineMapper(lineMapper);
    }


    @Override
    public void beforeStep(StepExecution stepExecution) {

        JobParameters jobParameters = stepExecution.getJobParameters();
        String filePath = jobParameters.getString("fileFullPath");
        log.info("filePath = [{}].", filePath);
        //TODO-
        assert filePath != null;
        Resource resource = null;
        try {
            resource = loadFileIfExists(filePath);
            setResource(resource);
        } catch (MagicDataReaderException e) {
            log.error("Reader failed to load file [{}].", filePath, e);
        }
//        FileSystemResource resource = new FileSystemResource(filePath);

    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }

}
