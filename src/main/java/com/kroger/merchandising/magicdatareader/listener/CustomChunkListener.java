package com.kroger.merchandising.magicdatareader.listener;

import com.kroger.merchandising.magicdatareader.service.FailedEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;

@Slf4j
@RequiredArgsConstructor
public class CustomChunkListener implements ChunkListener {
//    private final FailedEventService failedEventService;

    @Override
    public void beforeChunk(ChunkContext chunkContext) {
        log.info("read count {}",chunkContext.getStepContext().getStepExecution().getReadCount());
        log.info("Before chunk processing: {}" ,chunkContext.getStepContext().getStepName());
    }

    @Override
    public void afterChunk(ChunkContext chunkContext) {
//        failedEventService.persistFailedEventsIfExist();
        log.info("After chunk processing: {}", chunkContext.getStepContext().getStepName());
    }

    @Override
    public void afterChunkError(ChunkContext chunkContext) {
        log.error("Error during chunk processing: {}", chunkContext.getStepContext().getStepName());
    }
}
