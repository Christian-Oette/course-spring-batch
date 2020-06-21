package com.christianoette._C_listeners._02_job_execution_listener_component;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.stereotype.Component;

@Component
@JobScope
public class JobResultHolder {

    private static final Logger LOGGER = LogManager.getLogger(JobResultHolder.class);

    public JobResultHolder() {
        LOGGER.info("Job Result Holder created");
    }

    private String result;

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }
}
