package com.christianoette._D_scheduling;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class JobScheduler {

    private static final Logger LOGGER = LogManager.getLogger(JobScheduler.class);
    private static final String CRON_EVERY_5_SECONDS = "*/5 * * * * *";

    @Scheduled(initialDelay = 20_000, fixedDelay = 5_000)
    public void startJob() {
        runJob();
    }

    public void runJob() {
        LOGGER.info("Run a new job");
    }
}
