package de.oette.course.batch.chapter1_helloBatch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan(value = Chapter1Application.SCAN)
@EntityScan(value = Chapter1Application.SCAN)
@EnableBatchProcessing
public class Chapter1Application {

	public static final String SCAN = "de.oette.course.batch.chapter1_helloBatch";

	public static void main(String[] args) {
		SpringApplication.run(Chapter1Application.class, args);
	}

}
