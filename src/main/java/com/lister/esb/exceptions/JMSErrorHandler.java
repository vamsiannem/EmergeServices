package com.lister.esb.exceptions;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;

@Component("jmsErrorHandler")
public class JMSErrorHandler implements ErrorHandler{
    private Logger logger = Logger.getLogger(JMSErrorHandler.class);

    @Override
    public void handleError(Throwable throwable) {
      logger.warn(throwable.getMessage());
    }
}
