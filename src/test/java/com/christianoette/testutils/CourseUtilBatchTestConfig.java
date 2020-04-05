package com.christianoette.testutils;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.StepScopeTestExecutionListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestExecutionListeners;

@Configuration
@EnableBatchProcessing
@TestExecutionListeners(listeners = StepScopeTestExecutionListener.class,
        mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
public class CourseUtilBatchTestConfig {

    @Bean
    public JobLauncherTestUtils utils() {
        return new JobLauncherTestUtils();
    }
}
