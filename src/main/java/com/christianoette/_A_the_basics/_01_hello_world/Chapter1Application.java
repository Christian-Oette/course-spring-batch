package com.christianoette._A_the_basics._01_hello_world;

import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@EnableAutoConfiguration
@ComponentScan(value = Chapter1Application.SCAN)
@EntityScan(value = Chapter1Application.SCAN)
@EnableBatchProcessing
@PropertySource("/context/without-web-context.properties")
public class Chapter1Application {

	public static final String SCAN = "com.christianoette._A_the_basics._01_hello_world";

	public static void main(String[] args) throws JobExecutionException, InterruptedException {
		ConfigurableApplicationContext appContext = SpringApplication.run(Chapter1Application.class, args);

		TriggerJobService triggerJobService = appContext.getBean(TriggerJobService.class);
		triggerJobService.runJobs();
	}

}
