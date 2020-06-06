package com.christianoette._B_the_data_model._01_persistent_repository;

import com.christianoette.utils.components.trigger.CourseUtilsDefaultTrigger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
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
@PropertySource("/context/persistent-h2-context.properties")
public class Application {

	private static final Logger LOGGER = LogManager.getLogger(Application.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext appContext = SpringApplication.run(Application.class, args);
		CourseUtilsDefaultTrigger trigger = appContext.getBean(CourseUtilsDefaultTrigger.class);
		JobParameters jobParameters = new JobParametersBuilder()
				.addParameter("outputText", new JobParameter("Hello persistent spring batch job"))
				.toJobParameters();
		try {
			trigger.runJobs(jobParameters);
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
		}
	}
}
