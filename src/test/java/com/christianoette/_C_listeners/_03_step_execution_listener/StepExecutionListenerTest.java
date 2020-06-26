package com.christianoette._C_listeners._03_step_execution_listener;

import com.christianoette.testutils.CourseUtilBatchTestConfig;
import com.christianoette.utils.CourseUtilJobSummaryListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {StepExecutionListenerTest.TestConfig.class,
        CourseUtilBatchTestConfig.class, StepAndListenerInOneComponent.class})
class StepExecutionListenerTest {

    private static final Logger LOGGER = LogManager.getLogger(CourseUtilJobSummaryListener.class);

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
        private StepBuilderFactory stepBuilderFactory;

        @Autowired
        private StepAndListenerInOneComponent stepAndListenerInOneComponent;

        @Bean
        public Job job() {
            Job myJob = jobBuilderFactory.get("myJob")
                    .start(stepOne())
                    .next(stepTwo())
                    .build();
            return myJob;
        }

        @Bean
        @JobScope
        public Step stepOne() {
            return stepBuilderFactory.get("myFirstStep")
                    .tasklet(stepAndListenerInOneComponent)
                    .listener(stepAndListenerInOneComponent)
                    .build();
        }

        @Bean
        @JobScope
        public Step stepTwo() {
            return stepBuilderFactory.get("mySecondStep")
                    .tasklet((stepContribution, chunkContext) -> {
                        ExecutionContext executionContext = stepContribution.getStepExecution()
                                .getJobExecution().getExecutionContext();
                        int intermediateResult = executionContext.getInt("intermediateResult");
                        LOGGER.info("Intermediate Result is {}", intermediateResult);
                        return RepeatStatus.FINISHED;
                    }).build();
        }
    }

}
