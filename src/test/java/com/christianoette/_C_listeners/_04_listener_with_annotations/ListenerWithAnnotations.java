package com.christianoette._C_listeners._04_listener_with_annotations;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.BeforeChunk;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Component;

@Component
public class ListenerWithAnnotations {

    private static final Logger LOGGER = LogManager.getLogger(ListenerWithAnnotations.class);

    @BeforeChunk
    public void beforeChunk(ChunkContext chunkContext) {
        LOGGER.info("Before chunk");
    }

    @AfterChunk
    public void afterChunk(ChunkContext chunkContext) {
        LOGGER.info("After chunk");
    }
}
