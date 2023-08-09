package com.i2icellcelly.DGW.Common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DGWLogger {

    private static final Logger logger = LogManager.getLogger(DGWLogger.class);
    private static Runnable loggerRunnable;
    private static Thread loggerThread;

    public static void printInfoLogs(String message){
        loggerRunnable = () -> {
            logger.info(message);
        };
        loggerThread = new Thread(loggerRunnable);
        loggerThread.start();
    }

    public static void printWarningLogs(String message){
        loggerRunnable = () -> {
            logger.warn(message);
        };
        loggerThread = new Thread(loggerRunnable);
        loggerThread.start();
    }

}
