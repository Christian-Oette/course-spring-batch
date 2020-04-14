package com.christianoette._A_the_basics._01_hello_world_application;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Retention(RetentionPolicy.RUNTIME)
@Inherited()
@Qualifier("mySpecialJob")
public @interface MySpecialJob {
}
