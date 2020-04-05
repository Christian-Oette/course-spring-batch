package com.christianoette.commons;

import com.christianoette.chapter1.Chapter1Application;
import com.christianoette.chapter1.SumJobFactory;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootTest(classes = SumJobFactory.class)
@EnableBatchProcessing
class Chapter1IntegrityTest {

    @Test
    void test() {
        // start context once
    }
}
