package com.christianoette._C_listeners._04_listener_with_annotations;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class ReaderWithBeforeAndAfterStep implements ItemReader<String> {

    private static final Logger LOGGER = LogManager.getLogger(ReaderWithBeforeAndAfterStep.class);

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        LOGGER.info("Before step");
    }

    @AfterStep
    public void afterStep(StepExecution stepExecution) {
        LOGGER.info("After step");
    }

    @Override
    public String read() throws org.springframework.batch.item.ItemReaderException {
        return null;
    }
}
