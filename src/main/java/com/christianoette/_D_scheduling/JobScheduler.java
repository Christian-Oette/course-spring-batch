package com.christianoette._D_scheduling;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class JobScheduler {

    private static final Logger LOGGER = LogManager.getLogger(JobScheduler.class);
    private static final String CRON_EVERY_2_SECONDS = "*/2 * * * * *";

    public void startScheduledJob() {
        runJob();
    }

    public void runJob() {
        LOGGER.info("Run a new job");
    }
}
