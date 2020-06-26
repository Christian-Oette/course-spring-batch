package com.christianoette._E_validation_and_fault_tolerance._04_fault_tolerance_skip_exception;

import com.christianoette.testutils.CourseUtilBatchTestConfig;
import com.christianoette.utils.CourseUtilJobSummaryListener;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {SkipItemsWithExceptionTest.TestConfig.class, CourseUtilBatchTestConfig.class})
class SkipItemsWithExceptionTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    void runJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
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
                    .start(readerStep())
                    .listener(new CourseUtilJobSummaryListener())
                    .build();
        }

        @Bean
        public Step readerStep() {
            SimpleStepBuilder<SkipTestData, SkipTestData> simpleStepBuilder
                    = stepBuilderFactory.get("readJsonStep")
                    .chunk(1);

            return simpleStepBuilder.reader(reader())
                    .processor(new ItemProcessor<SkipTestData, SkipTestData>() {
                        @Override
                        public SkipTestData process(SkipTestData item) throws Exception {
                            if (item.skipIt) {
                                throw new CustomSkipException();
                            } else {
                                return item;
                            }
                        }
                    })
                    .faultTolerant()
                    .skip(CustomSkipException.class)
                    .skipLimit(2)
                    .writer(writer())
                    .build();
        }

        @Bean
        public JsonItemReader<SkipTestData> reader() {
            File file;
            try {
                file = ResourceUtils.getFile("classpath:files/_E/skipTest.json");
            } catch (FileNotFoundException ex) {
                throw new IllegalArgumentException(ex);
            }

            return new JsonItemReaderBuilder<SkipTestData>()
                    .jsonObjectReader(new JacksonJsonObjectReader<>(SkipTestData.class))
                    .resource(new FileSystemResource(file))
                    .name("jsonItemReader")
                    .build();
        }

        @Bean
        public JsonFileItemWriter<SkipTestData> writer() {
            Resource outputResource = new FileSystemResource("output/skipOutput2.json");

            return new JsonFileItemWriterBuilder<SkipTestData>()
                    .jsonObjectMarshaller(new JacksonJsonObjectMarshaller<>())
                    .resource(outputResource)
                    .name("jsonItemWriter")
                    .build();
        }

        public static class SkipTestData {
            public String item;
            public boolean skipIt;

            @Override
            public String toString() {
                return "SkipTestData{" +
                        "item='" + item + '\'' +
                        ", skipIt=" + skipIt +
                        '}';
            }
        }
    }
}
