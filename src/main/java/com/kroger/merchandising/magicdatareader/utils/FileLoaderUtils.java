package com.kroger.merchandising.magicdatareader.utils;

import com.kroger.merchandising.magicdatareader.configuration.exception.MagicDataReaderException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

@Slf4j
public class FileLoaderUtils {

    private static final ResourceLoader resourceLoader = new DefaultResourceLoader();

    private FileLoaderUtils() {}

    public static Resource loadFileIfExists(String fileFullPath) throws MagicDataReaderException {
        try {
            Resource resource = resourceLoader.getResource("file:" + fileFullPath);
            if (!resource.exists()) {
                log.error("File {} was not found..", fileFullPath);
                throw new MagicDataReaderException("File " + fileFullPath + " was not found..");
            }
            return resource;
        } catch (Exception e) {
            log.error("File {} was not found..", fileFullPath, e);
            throw new RuntimeException("File: " + fileFullPath + " was not found..", e);
        }
    }
}
