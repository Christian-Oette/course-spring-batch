package com.christianoette._C_listeners._02_job_execution_listener_component;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@JobScope
public class JobListenerAsComponent implements JobExecutionListener {

    private static final Logger LOGGER = LogManager.getLogger(JobListenerAsComponent.class);

    @Value("#{jobParameters['outputText']}")
    private String outputText;

    @Autowired
    private JobResultHolder jobResultHolder;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        LOGGER.info("Job {} started: {}", jobExecution.getJobId(), outputText);
        LOGGER.info("Result is {}", jobResultHolder.getResult());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        LOGGER.info("Result is {}", jobResultHolder.getResult());
        LOGGER.info("Job {} ended with status {}", jobExecution.getJobId(), jobExecution.getStatus());
    }
}
