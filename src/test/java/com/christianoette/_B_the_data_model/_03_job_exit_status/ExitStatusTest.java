package com.christianoette._B_the_data_model._03_job_exit_status;

import com.christianoette.testutils.CourseUtilBatchTestConfig;
import com.christianoette.utils.CourseUtilJobSummaryListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {ExitStatusTest.TestConfig.class, CourseUtilBatchTestConfig.class})
class ExitStatusTest {

    private static final Logger LOGGER = LogManager.getLogger(ExitStatusTest.class);

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    void runJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addParameter("id", new JobParameter(UUID.randomUUID().toString()))
                .toJobParameters();
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);

        ExitStatus exitStatus = jobExecution.getExitStatus();
        LOGGER.info(exitStatus);
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
            Job myJob = jobBuilderFactory.get("myJob")
                    .start(step())
                    .listener(new CourseUtilJobSummaryListener())
                    .build();
            return myJob;
        }


        @Bean
        @JobScope
        public Step step() {
            return stepBuilderFactory.get("myFirstStep")
                    .tasklet((stepContribution, chunkContext) -> {
                        return RepeatStatus.FINISHED;
                    })
                    .build();
        }


    }

}
