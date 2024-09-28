package com.kroger.merchandising.magicdatareader.batch.reader;


import com.kroger.merchandising.magicdatareader.configuration.exception.MagicDataReaderException;
import com.kroger.merchandising.magicdatareader.domain.DataItem;
import com.kroger.merchandising.magicdatareader.service.FailedEventService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;

import static com.kroger.merchandising.magicdatareader.utils.FileLoaderUtils.loadFileIfExists;

@Slf4j
@Component
public class DataItemReader extends FlatFileItemReader<DataItem> implements StepExecutionListener {
    private final Validator factory;
    private final FailedEventService failedEventService;

    public DataItemReader(FailedEventService failedEventService) {
            this.failedEventService = failedEventService;
//            setStrict(true);
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
            factory = Validation.buildDefaultValidatorFactory().getValidator();
    }

        @Override
        public DataItem doRead() throws Exception {
            DataItem dataItem = super.doRead();
            if (Objects.isNull(dataItem)) return null;
            Set<ConstraintViolation<DataItem>> violations = this.factory.validate(dataItem);
            if (!violations.isEmpty()) {
                failedEventService.handleBadRecord(dataItem.dataAsTextLine());
                violations.forEach(violation -> log.error(violation.getMessage()));
                String errorMsg = String.format("The input has validation failed. Data is '%s'", dataItem);
                throw new FlatFileParseException(errorMsg, Objects.toString(dataItem));
            }
            else {
                return dataItem;
            }
        }

        @Override
        public void beforeStep(StepExecution stepExecution) {
            JobParameters jobParameters = stepExecution.getJobParameters();
            String fileInput = jobParameters.getString("fileInput");
            assert fileInput != null;
            Resource resource = null;
            try {
                resource = loadFileIfExists(fileInput);
                setResource(resource);
            } catch (MagicDataReaderException e) {
                log.error("Reader failed to load file {}.", fileInput, e);
            }
        }

        @Override
        public ExitStatus afterStep(StepExecution stepExecution) {
            return null;
        }

}
