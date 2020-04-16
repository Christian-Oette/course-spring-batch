package com.christianoette._A_the_basics._01_hello_world_application.jobconfig;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Inherited()
@Qualifier("myJob")
public @interface MyJob {
}
