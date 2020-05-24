package com.christianoette._A_the_basics._02_read_write_process;

import com.christianoette.testutils.CourseUtilBatchTestConfig;
import com.christianoette.utils.CourseUtils;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.batch.item.support.PassThroughItemProcessor;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {ProcessorTest.TestConfig.class, CourseUtilBatchTestConfig.class})
class ProcessorTest {

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
                    .build();
        }

        @Bean
        public Step readerStep() {
            SimpleStepBuilder<InputAndOutputData, InputAndOutputData> simpleStepBuilder
                    = stepBuilderFactory.get("readJsonStep")
                    .chunk(1);

            return simpleStepBuilder.reader(reader())
                    .processor(new PassThroughItemProcessor<>())
                    .writer(writer()).build();
        }

        @Bean
        public JsonItemReader<InputAndOutputData> reader() {
            File file;
            try {
                file = ResourceUtils.getFile("classpath:files/_A/input.json");
            } catch (FileNotFoundException ex) {
                throw new IllegalArgumentException(ex);
            }

            return new JsonItemReaderBuilder<InputAndOutputData>()
                    .jsonObjectReader(new JacksonJsonObjectReader<>(InputAndOutputData.class))
                    .resource(new FileSystemResource(file))
                    .name("jsonItemReader")
                    .build();
        }

        @Bean
        public JsonFileItemWriter<InputAndOutputData> writer() {
            Resource outputResource = new FileSystemResource("output/output.json");

            return new JsonFileItemWriterBuilder<InputAndOutputData>()
                    .jsonObjectMarshaller(new JacksonJsonObjectMarshaller<>())
                    .resource(outputResource)
                    .name("jsonItemWriter")
                    .build();
        }

        public static class InputAndOutputData {
            public String value;

            @Override
            public String toString() {
                return "InputAndOutputData{" +
                        "value='" + value + '\'' +
                        '}';
            }
        }
    }
}
