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
                log.error("File {} does not exist", fileFullPath);
                throw new MagicDataReaderException("Folder " + fileFullPath + " does not exist");
            }
            return resource;
        } catch (Exception e) {
            log.error("Unable to load any magic file from: {}", fileFullPath, e);
            throw new MagicDataReaderException("Unable to load any magic file from: " + fileFullPath, e);
        }
    }
}
