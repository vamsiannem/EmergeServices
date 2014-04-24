package com.lister.esb.mq;


import com.lister.esb.model.Request;
import com.lister.esb.processors.IRequestDelegate;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.Map;

public class POJOESBMessageListener implements MessageDelegate{

     private Logger logger = Logger.getLogger(POJOESBMessageListener.class);
    private IRequestDelegate requestDelegate;

    public POJOESBMessageListener(IRequestDelegate requestDelegate) {
        this.requestDelegate = requestDelegate;
    }

    public void handleMessage(String message) {
        logger.info("string");
    }

    public void handleMessage(Map message) {
        logger.info("map");
    }

    public void handleMessage(byte[] message) {
       logger.info("byte message");
    }

    public void handleMessage(Serializable message) {
        logger.info("Serializable message");
        Request request = (Request) message;
        requestDelegate.transfer(request);
    }

    public void setRequestDelegate(IRequestDelegate requestDelegate) {
        this.requestDelegate = requestDelegate;
    }


}
