package com.christianoette._B_the_data_model._01_persistent_repository;

import com.christianoette.utils.components.trigger.CourseUtilsDefaultTrigger;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@EnableAutoConfiguration
@ComponentScan(basePackageClasses = {Application.class, CourseUtilsDefaultTrigger.class})
@EnableBatchProcessing
@EntityScan(basePackageClasses = Application.class)
@PropertySource("/context/without-web-context.properties")
public class Application {

	public static void main(String[] args) throws JobExecutionException, InterruptedException {
		ConfigurableApplicationContext appContext = SpringApplication.run(Application.class, args);
		CourseUtilsDefaultTrigger trigger = appContext.getBean(CourseUtilsDefaultTrigger.class);
		trigger.runJobs();
	}
}
