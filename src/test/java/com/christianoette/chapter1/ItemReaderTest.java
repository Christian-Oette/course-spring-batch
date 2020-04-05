package com.christianoette.chapter1;

import com.christianoette.testutils.CourseUtilBatchTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.*;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {ItemReaderTest.TestConfig.class, CourseUtilBatchTestConfig.class})
class ItemReaderTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    void runJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addParameter("outputText", new JobParameter("Hello Spring Batch"))
                .toJobParameters();

        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
    }

    @SuppressWarnings("WeakerAccess")
    @Configuration
    static class TestConfig {

        @Autowired
        private JobBuilderFactory jobBuilderFactory;

        @Autowired
        private StepBuilderFactory stepBuilderFactory;

        @Bean
        public Job job() {
            return jobBuilderFactory.get("myJob")
                    .start(step())
                    .build();
        }

        @Bean
        public Step step() {
            return stepBuilderFactory.get("reader")
                    .chunk(1)
                    .reader(reader())
                    .writer(new ItemWriter<Object>() {
                        @Override
                        public void write(List<?> items) throws Exception {
                            System.out.println(items);
                        }
                    })
                    .build();
        }

        @Bean
        public JsonItemReader<Input> reader() {
            File file = null;
            try {
                file = ResourceUtils.getFile("classpath:files/input.json");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            return new JsonItemReaderBuilder<Input>()
                    .jsonObjectReader(new JacksonJsonObjectReader<>(Input.class))
                    .resource(new FileSystemResource(file))
                    .name("tradeJsonItemReader")
                    .build();
        }

        public static class Input {
            public String value;

            @Override
            public String toString() {
                return "Data{" +
                        "value='" + value + '\'' +
                        '}';
            }
        }
    }

}
