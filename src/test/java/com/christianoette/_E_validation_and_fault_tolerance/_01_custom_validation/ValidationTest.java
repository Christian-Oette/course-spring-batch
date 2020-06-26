package com.christianoette._E_validation_and_fault_tolerance._01_custom_validation;

import com.christianoette.testutils.CourseUtilBatchTestConfig;
import com.christianoette.utils.CourseUtilJobSummaryListener;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {ValidationTest.TestConfig.class, CourseUtilBatchTestConfig.class})
class ValidationTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    void runJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addParameter("parameterOne", new JobParameter(35L))
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
                    .start(stepOne())
                    .validator(new JobParametersValidator() {
                        @Override
                        public void validate(JobParameters parameters) throws JobParametersInvalidException {
                            Long parameterOne = parameters.getLong("parameterOne");
                            if (parameterOne == null || parameterOne < 30L) {
                                throw new JobParametersInvalidException("parameterOne must be greater then 30");
                            }
                        }
                    })
                    .listener(new CourseUtilJobSummaryListener())
                    .build();
        }

        @Bean
        @JobScope
        public Step stepOne() {
            return stepBuilderFactory.get("dummyStep")
                    .tasklet((stepContribution, chunkContext) -> RepeatStatus.FINISHED)
                    .build();
        }
    }

}
