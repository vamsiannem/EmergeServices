package com.lister.esb.utils;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;

import java.text.SimpleDateFormat;

public class ESBLogger {

    private Logger logger = Logger.getLogger(ESBLogger.class);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public ESBLogger() {
    }

    public void logEntry(JoinPoint joinPoint){
        logMessage("Start: " + joinPoint.getTarget().getClass().getSimpleName() + ": " + joinPoint.getSignature().getName() );
    }

    public void logExit(JoinPoint joinPoint){
        logMessage("End:" + joinPoint.getTarget().getClass().getSimpleName() + ": "  + joinPoint.getSignature().getName() );
    }

    public void logAroundAdvice(JoinPoint joinPoint) {
        logMessage("Currently in " + joinPoint.getSignature().getName() + "()");
    }

    public void logExitAfterReturn(JoinPoint joinPoint) {
        logMessage("Currently in " + joinPoint.getSignature().getName() + "()");
    }

    public void logAfterThrowsAdvice(JoinPoint joinPoint) {
        logMessage("Currently in " + joinPoint.getSignature().getName() + "()");
    }


    public void logMessage(String message){
        logger.info(message);
    }
}
