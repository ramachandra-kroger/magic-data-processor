package com.kroger.merchandising.magicdatareader.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;

@Slf4j
public class CustomChunkListener implements ChunkListener {
    @Override
    public void beforeChunk(ChunkContext chunkContext) {
        log.info("Before chunk processing: {}" ,chunkContext.getStepContext().getStepName());
    }

    @Override
    public void afterChunk(ChunkContext chunkContext) {
        log.info("After chunk processing: {}", chunkContext.getStepContext().getStepName());
    }

    @Override
    public void afterChunkError(ChunkContext chunkContext) {
        log.error("Error during chunk processing: {}", chunkContext.getStepContext().getStepName());
    }
}
