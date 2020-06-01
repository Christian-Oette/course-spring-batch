package com.christianoette.utils.components.trigger;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CourseUtilsDefaultTrigger {

    private final JobLauncher jobLauncher;

    private final Job job;

    public CourseUtilsDefaultTrigger(JobLauncher jobLauncher,
                                     @Autowired(required = false) Job job) {
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    public void runJobs() throws JobExecutionException {
        runJobs(new JobParameters());
    }

    public void runJobs(JobParameters jobParams) throws JobExecutionException {
        Objects.requireNonNull(job, "You haven't configured a job yet.");
        jobLauncher.run(job, jobParams);
    }
}
