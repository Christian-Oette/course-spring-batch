package com.christianoette.chapter1;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SumJobFactory {

    private static final String JOB_NAME = "myFirstJob";

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job createJob() {
        return jobBuilderFactory.get(JOB_NAME)
                .start(simpleStep())
                .build();
    }

    @Bean
    public Step simpleStep() {
        return stepBuilderFactory.get("outputStep")
                .tasklet(myTasklet())
                .build();
    }

    @Bean
    public Tasklet myTasklet() {
        return (stepContribution, chunkContext) -> {
            System.out.println("Hello World");
            return RepeatStatus.FINISHED;
        };
    }

}
