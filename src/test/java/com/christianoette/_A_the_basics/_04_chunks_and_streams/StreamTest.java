package com.christianoette._A_the_basics._04_chunks_and_streams;

import com.christianoette.testutils.CourseUtilBatchTestConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.item.support.PassThroughItemProcessor;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {StreamTest.TestConfig.class, CourseUtilBatchTestConfig.class})
class StreamTest {

    private static final Logger LOGGER = LogManager.getLogger(StreamTest.class);

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    @Disabled
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
        private JobRepository jobRepository;

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
            SimpleStepBuilder<String, String> chunk = stepBuilderFactory.get("jsonItemReader")
                    .repository(jobRepository)
                    .chunk(4);

            return chunk
                    .reader(null)
                    .processor(new PassThroughItemProcessor<>())
                    .writer(null)
                    .build();
        }


    }

}
