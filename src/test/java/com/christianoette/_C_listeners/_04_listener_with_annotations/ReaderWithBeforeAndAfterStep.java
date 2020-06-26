package com.christianoette._C_listeners._04_listener_with_annotations;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

@Component
public class ReaderWithBeforeAndAfterStep implements ItemReader<String> {

    private static final Logger LOGGER = LogManager.getLogger(ReaderWithBeforeAndAfterStep.class);

    @Override
    public String read() throws org.springframework.batch.item.ItemReaderException {
        return null;
    }
}
