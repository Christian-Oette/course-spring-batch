package com.christianoette._C_listeners._02_job_execution_listener_component;

import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.stereotype.Component;

@Component
@JobScope
public class JobResultHolder {

    public String result;
}
