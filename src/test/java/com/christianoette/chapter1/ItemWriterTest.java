package com.christianoette.chapter1;

import com.christianoette.testutils.CourseUtilBatchTestConfig;
import com.christianoette.utils.CourseUtils;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {ItemWriterTest.TestConfig.class, CourseUtilBatchTestConfig.class})
class ItemWriterTest {

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
        private JobRepository jobRepository;

        @Autowired
        private PlatformTransactionManager transactionManager;

        @Bean
        public Job job() {
            return jobBuilderFactory.get("myJob")
                    .start(step())
                    .build();
        }

        @Bean
        public Step step() {
            SimpleStepBuilder<InAndOutData, InAndOutData> chunk = new StepBuilder("reader")
                    .repository(jobRepository)
                    .transactionManager(transactionManager)
                    .chunk(1);

            return chunk.reader(reader())
                    .processor(new PassThroughItemProcessor<>())
                    .writer(writer())
                    .build();
        }

        @Bean
        public JsonItemReader<InAndOutData> reader() {
            Resource inputResource = CourseUtils.getFileResource("classpath:files/input.json");

            return new JsonItemReaderBuilder<InAndOutData>()
                    .jsonObjectReader(new JacksonJsonObjectReader<>(InAndOutData.class))
                    .resource(inputResource)
                    .name("tradeJsonItemReader")
                    .build();
        }

        @Bean
        public JsonFileItemWriter<InAndOutData> writer() {
            Resource outputResource = new FileSystemResource("output/output.json");

            return new JsonFileItemWriterBuilder<InAndOutData>()
                    .jsonObjectMarshaller(new JacksonJsonObjectMarshaller<>())
                    .resource(outputResource)
                    .name("tradeJsonItemReader")
                    .build();
        }

        public static class InAndOutData {
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
