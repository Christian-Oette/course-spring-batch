package com.christianoette._C_listeners._04_listener_with_annotations;


import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootTest(classes =
        {AnnotationListenerTest.TestConfig.class, ReaderWithBeforeAndAfterStep.class
        , ListenerWithAnnotations.class
        })
class AnnotationListenerTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    void test() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
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
        private ReaderWithBeforeAndAfterStep readerWithBeforeAndAfterStep;

        @Autowired
        private ListenerWithAnnotations listenerWithAnnotations;

        @Bean
        public Job annotationListenerTest() {
            Step step = stepBuilderFactory.get("step")
                    .chunk(1)
                    .reader(readerWithBeforeAndAfterStep)
                    .writer(items -> {

                    })
                    .listener(listenerWithAnnotations)
                    .build();

            return jobBuilderFactory.get("job")
                    .start(step)
                    .build();
        }

        @Bean
        public JobLauncherTestUtils utils() {
            return new JobLauncherTestUtils();
        }
    }
}
