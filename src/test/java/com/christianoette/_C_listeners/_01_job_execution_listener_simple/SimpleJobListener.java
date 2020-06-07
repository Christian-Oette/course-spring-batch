package com.christianoette._C_listeners._01_job_execution_listener_simple;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class SimpleJobListener implements JobExecutionListener {

    private static final Logger LOGGER = LogManager.getLogger(SimpleJobListener.class);


    @Override
    public void beforeJob(JobExecution jobExecution) {
        LOGGER.info("Job {} started", jobExecution.getJobId());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        LOGGER.info("Job {} ended with status {}", jobExecution.getJobId(),
                jobExecution.getExitStatus());
    }
}
