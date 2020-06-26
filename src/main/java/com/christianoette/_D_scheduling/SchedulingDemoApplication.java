package com.christianoette._D_scheduling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAutoConfiguration
@ComponentScan(basePackageClasses = SchedulingDemoApplication.class)
@EntityScan(basePackageClasses = SchedulingDemoApplication.class)
@EnableScheduling
@PropertySource("/context/without-web-schedule.properties")
public class SchedulingDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchedulingDemoApplication.class, args);
	}

}
