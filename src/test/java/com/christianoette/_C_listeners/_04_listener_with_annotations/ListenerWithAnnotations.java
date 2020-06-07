package com.christianoette._C_listeners._04_listener_with_annotations;

import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.BeforeChunk;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Component;

@Component
public class ListenerWithAnnotations {

    @BeforeChunk
    public void before(ChunkContext chunkContext) {
        System.out.println("before chunk");
    }

    @AfterChunk
    public void after(ChunkContext chunkContext) {
        System.out.println("after chunk");
    }
}
