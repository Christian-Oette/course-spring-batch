package com.christianoette._A_the_basics._01_hello_world_application;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobFactory2 {

    private static final String JOB_NAME = "myFirstJob2";

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    @MySpecialJob
    public Job createJob2() {
        return jobBuilderFactory.get(JOB_NAME)
                .start(simpleStep2())
                .build();
    }

    @Bean
    public Step simpleStep2() {
        return stepBuilderFactory.get("outputStep2")
                .tasklet(myTasklet2())
                .build();
    }

    @Bean
    public Tasklet myTasklet2() {
        return (stepContribution, chunkContext) -> {
            System.out.println("Hello World 2nd version");
            return RepeatStatus.FINISHED;
        };
    }

}
