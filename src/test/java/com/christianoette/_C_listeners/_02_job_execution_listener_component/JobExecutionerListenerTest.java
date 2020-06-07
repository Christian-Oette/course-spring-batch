package com.christianoette._C_listeners._02_job_execution_listener_component;


import org.junit.jupiter.api.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@SpringBootTest(classes = {JobExecutionerListenerTest.TestConfig.class,
        JobResultHolder.class,
        JobListenerAsComponent.class})
class JobExecutionerListenerTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    void test() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addParameter("outputText", new JobParameter("Hello Spring Batch"))
                .toJobParameters();
        jobLauncherTestUtils.launchJob(jobParameters);
    }

    @Configuration
    @EnableBatchProcessing
    static class TestConfig {

        @Autowired
        private JobBuilderFactory jobBuilderFactory;

        @Autowired
        private StepBuilderFactory stepBuilderFactory;

        @Autowired
        private JobListenerAsComponent jobListenerAsComponent;

        @Autowired
        private JobResultHolder jobResultHolder;

        @Bean
        public Job executionListenerJob() {
            Step step = stepBuilderFactory.get("executionListenerJob")
                    .tasklet((contribution, chunkContext) -> {
                        Map<String, Object> jobParameters = chunkContext.getStepContext()
                                .getJobParameters();
                        Object outputText = jobParameters.get("outputText");
                        System.out.println(outputText);
                        jobResultHolder.result = "GLOBAL-JOB_RESULT";

                        return RepeatStatus.FINISHED;
                    }).build();

            return jobBuilderFactory.get("helloWorldJob")
                    .start(step)
                    .listener(jobListenerAsComponent)
                    .build();
        }

        @Bean
        public JobLauncherTestUtils utils() {
            return new JobLauncherTestUtils();
        }
    }
}
