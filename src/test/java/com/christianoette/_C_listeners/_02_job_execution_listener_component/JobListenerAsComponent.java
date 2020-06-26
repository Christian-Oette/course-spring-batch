package com.christianoette._C_listeners._02_job_execution_listener_component;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class JobListenerAsComponent implements JobExecutionListener {

    private static final Logger LOGGER = LogManager.getLogger(JobListenerAsComponent.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        LOGGER.info("Job {} started.", jobExecution.getJobId());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        LOGGER.info("Job {} ended with status {}", jobExecution.getJobId(), jobExecution.getStatus());
    }
}
