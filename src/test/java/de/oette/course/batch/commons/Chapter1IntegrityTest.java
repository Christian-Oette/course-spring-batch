package de.oette.course.batch.commons;

import de.oette.course.batch.chapter1_helloBatch.Chapter1Application;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootTest(classes = Chapter1IntegrityTest.TestConfig.class)
@EnableBatchProcessing
public class Chapter1IntegrityTest {

    @Test
    void test() {
        // start context once
    }

    @ComponentScan(value = Chapter1Application.SCAN)
    @Configuration
    public static class TestConfig {

    }
}
