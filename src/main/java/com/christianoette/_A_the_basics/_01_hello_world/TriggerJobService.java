package com.christianoette._A_the_basics._01_hello_world;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TriggerJobService {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job createJob;

    public void runJobs() throws JobExecutionException, InterruptedException {
        JobParameters jobParams = new JobParameters();
        Thread.sleep(3000);
        jobLauncher.run(createJob, jobParams);
        Thread.sleep(3000);
        jobLauncher.run(createJob, jobParams);
    }
}
