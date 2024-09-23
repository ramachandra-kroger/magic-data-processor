package com.kroger.merchandising.magicdatareader.utils;

import com.kroger.merchandising.magicdatareader.configuration.exception.MagicDataReaderException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;

import java.io.File;

@Slf4j
public class FileLoaderUtils {
    private FileLoaderUtils() {}

    public static Resource loadFileIfExists(String folderFullPath) throws MagicDataReaderException {
        try {
            File folder = new File(folderFullPath);
            if (!folder.exists()) {
                log.error("Folder {} does not exist", folderFullPath);
                throw new MagicDataReaderException("Folder " + folderFullPath + " does not exist");
            }
            File[] files = folder.listFiles();
            if (files == null ) {
                log.error("Folder {} is empty", folderFullPath);
                throw new MagicDataReaderException("Folder " + folderFullPath + " is empty");
            }
            return files.length > 0 ? (Resource) files[0] : null;
        } catch (Exception e) {
            log.error("Unable to load any magic file from: {}", folderFullPath, e);
            throw new MagicDataReaderException("Unable to load any magic file from: " + folderFullPath , e);
        }
    }
}
