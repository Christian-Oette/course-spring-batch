package com.christianoette._A_the_basics._03_scopes;

import com.christianoette.testutils.CourseUtilBatchTestConfig;
import com.christianoette.utils.CourseUtils;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
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
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.transaction.PlatformTransactionManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {ExecutionContextTest.TestConfig.class, CourseUtilBatchTestConfig.class})
class ExecutionContextTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    void runJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addParameter("outputText", new JobParameter("Hello Spring Batch"))
                .addParameter("inputPath", new JobParameter("classpath:files/input.json"))
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
            SimpleStepBuilder<InputData, OutputData> chunk = new StepBuilder("jsonItemReader")
                    .repository(jobRepository)
                    .transactionManager(transactionManager)
                    .chunk(1);

            return chunk.reader(jsonItemReader(null))
                    .processor(processor())
                    .writer(writer())
                    .build();
        }

        @Bean
        public ItemProcessor<InputData, OutputData> processor() {
            return item -> {
                OutputData outputData = new OutputData();
                outputData.value = item.value.toUpperCase();
                return outputData;
            };
        }

        @Bean
        @StepScope
        public JsonItemReader<InputData> jsonItemReader(@Value("#{jobParameters['inputPath']}") String inputPath) {
            Resource inputResource = CourseUtils.getFileResource(inputPath);

            return new JsonItemReaderBuilder<InputData>()
                    .jsonObjectReader(new JacksonJsonObjectReader<>(InputData.class))
                    .resource(inputResource)
                    .name("tradeJsonItemReader")
                    .build();
        }

        @Bean
        public JsonFileItemWriter<OutputData> writer() {
            Resource outputResource = new FileSystemResource("output/output.json");

            return new JsonFileItemWriterBuilder<OutputData>()
                    .jsonObjectMarshaller(new JacksonJsonObjectMarshaller<>())
                    .resource(outputResource)
                    .name("tradeJsonItemReader")
                    .build();
        }

        public static class InputData {
            public String value;

            @Override
            public String toString() {
                return "Data{" +
                        "value='" + value + '\'' +
                        '}';
            }
        }

        public static class OutputData {
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
