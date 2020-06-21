package com.christianoette._F_controlling_flow._02_flow_as_bean;

import com.christianoette.testutils.CourseUtilBatchTestConfig;
import com.christianoette.utils.CourseUtilJobSummaryListener;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {FlowAsBeanTest.TestConfig.class, CourseUtilBatchTestConfig.class})
class FlowAsBeanTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    void runJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addParameter("parameterOne", new JobParameter(25L))
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
                    .start(flow())
                    .end()
                    .listener(new CourseUtilJobSummaryListener())
                    .build();
        }

        @Bean
        public Flow flow() {
            return new FlowBuilder<SimpleFlow>("fallbackFlow")
                    .start(stepOne())
                    .on("COMPLETED")
                    .end()
                    .on("FAILED")
                    .to(fallBackStep())
                    .end();
        }

        @Bean
        public Step stepOne() {
            return stepBuilderFactory.get("stepOne")
                    .tasklet((stepContribution, chunkContext) -> {
                        throw new RuntimeException("failed");
                        //return RepeatStatus.FINISHED;
                    })
                    .build();
        }


        @Bean
        public Step fallBackStep() {
            return stepBuilderFactory.get("fallBackStep")
                    .tasklet((stepContribution, chunkContext) -> RepeatStatus.FINISHED)
                    .build();
        }
    }

}
