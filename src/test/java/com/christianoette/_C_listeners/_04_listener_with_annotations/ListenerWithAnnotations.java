package com.christianoette._C_listeners._04_listener_with_annotations;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class ListenerWithAnnotations {

    private static final Logger LOGGER = LogManager.getLogger(ListenerWithAnnotations.class);


}
