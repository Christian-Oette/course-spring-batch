package com.christianoette._C_listeners._01_job_execution_listener;

import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.stereotype.Component;

@Component
@JobScope
public class JobResultHolder {

    public String result;
}
