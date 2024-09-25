package com.kroger.merchandising.magicdatareader.utils;

import com.kroger.merchandising.magicdatareader.configuration.exception.MagicDataReaderException;

import com.kroger.merchandising.magicdatareader.domain.DataItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@Component
public class FakeMagicData implements CommandLineRunner {
    private final ResourceLoader resourceLoader;

    @Value("${app.input}")
    private String fileInput;

    public FakeMagicData(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    private Random random = new Random();

    public void generateFakeItems() throws MagicDataReaderException {
        List<String> dataItems = new ArrayList<>();
        for (int counter = 0; counter < 100; counter++) {
            DataItem dataItem = generateFakeItem();
            dataItems.add(dataItem.dataAsTextLine());
        }
        writeDataItemsToFile(dataItems);
    }

    private DataItem generateFakeItem() {


        return DataItem.builder()
                .locationNumber("0000" + random.nextInt(9))
                .upc("0000" + LocalTime.now().getNano())
                .quantitie1("")
                .permanentPrice("0000" + random.nextInt(100, 999))
                .quantitie2("")
                .temporaryPrice("0000" + random.nextInt(100, 999))
                .effectiveDateFrom(LocalDate.of(2024, 7, random.nextInt(1,28)).toString())
                .effectiveDateTo(LocalDate.of(2024, 8, random.nextInt(1,28)).toString())
                .timingFlag(random.nextInt(5) % 2 == 0 ? "C" : "F")
                .durationFlag(random.nextInt(5) % 2 == 0 ? "T" : "P")
                .sku(random.nextInt(10000000, 90000000) + "")
                .magicCoupon(random.nextInt(10000000, 90000000) + "")
                .couponUpc("000000000" + random.nextInt(1000, 9999))
                .build();
    }

    private void writeDataItemsToFile(List<String> lines) throws MagicDataReaderException {
        ;
        File file = null;
        try {
            file = getFileFromResource(fileInput);
        } catch (Exception e) {
            log.error("Error while getting file location: {} ", e.getMessage());
            throw new MagicDataReaderException(e);
        }
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            for (String line : lines) {
                fileOutputStream.write(line.getBytes(StandardCharsets.UTF_8));
            }
            log.info("{} data items has been written to file: {}", lines.size(), file.getAbsolutePath());
        } catch (Exception e) {
            log.error("Error while opening file: {} ", e.getMessage());
            throw new MagicDataReaderException(e);
        }
    }

    private File getFileFromResource(String fileName) throws MagicDataReaderException {
        File file = null;
        Resource resource = resourceLoader.getResource("file:"+fileName);
        try {
            file = resource.getFile();
        } catch (IOException e) {
            log.error("Error while trying to read file: {} ", e.getMessage());
            throw new MagicDataReaderException(e);
        }
        return file;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Running FakeMagicData...");
        generateFakeItems();
        log.info("Finished FakeMagicData!");
    }
}
