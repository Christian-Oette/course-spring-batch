package com.christianoette._A_the_basics;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

class ImportTest {

    private static final Logger LOGGER = LogManager.getLogger(ImportTest.class);

    @Test
    void testIfProjectIsReady() {
        LOGGER.info("Project import completed. You are good to go!");
    }
}
