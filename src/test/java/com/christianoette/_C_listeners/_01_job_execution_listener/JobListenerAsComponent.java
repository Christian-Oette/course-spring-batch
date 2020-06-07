package com.christianoette._C_listeners._01_job_execution_listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@JobScope
public class JobListenerAsComponent implements JobExecutionListener {

    @Value("#{jobParameters['outputText']}")
    private String text;

    @Autowired
    private JobResultHolder jobResultHolder;


    public JobListenerAsComponent() {
        System.out.println("created with text " + text);
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("Global result before job "+jobResultHolder.result);
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println("Global result after job "+jobResultHolder.result);
    }
}
