package com.christianoette.commons;

import com.christianoette._A_the_basics._01_hello_world_application.JobFactory;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = JobFactory.class)
@EnableBatchProcessing
class Chapter1IntegrityTest {

    @Test
    void test() {
        // start context once
    }
}
