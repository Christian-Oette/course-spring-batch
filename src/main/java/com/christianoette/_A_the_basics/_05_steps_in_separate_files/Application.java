package com.christianoette._A_the_basics._05_steps_in_separate_files;

import com.christianoette.utils.components.trigger.CourseUtilsDefaultTrigger;
import org.springframework.batch.core.JobExecutionException;
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
@EntityScan(basePackageClasses = Application.class)
@EnableBatchProcessing
@PropertySource("/context/without-web-context.properties")
public class Application {

	public static void main(String[] args) throws JobExecutionException, InterruptedException {
		ConfigurableApplicationContext appContext = SpringApplication.run(Application.class, args);
		CourseUtilsDefaultTrigger trigger = appContext.getBean(CourseUtilsDefaultTrigger.class);
		JobParameters jobParams = new JobParametersBuilder()
				.addParameter("inputPath", new JobParameter("classpath:files/_A/input.json"))
				.addParameter("outputPath", new JobParameter("output/upperCaseOutput.json"))
				.toJobParameters();
		trigger.runJobs(jobParams);
	}
}
