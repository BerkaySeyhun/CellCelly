package com.i2icellcelly.DGW.Common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A logger class for the DGW system. The logging functions run in separate threads.
 */
public class DGWLogger {

    private static final Logger logger = LogManager.getLogger(DGWLogger.class);
    private static Runnable loggerRunnable;
    private static Thread loggerThread;

    //Prints info logs.
    public static void printInfoLogs(String message){
        loggerRunnable = () -> logger.info(message);
        loggerThread = new Thread(loggerRunnable);
        loggerThread.start();
    }

    //Prints warning logs.
    public static void printWarningLogs(String message){
        loggerRunnable = () -> logger.warn(message);
        loggerThread = new Thread(loggerRunnable);
        loggerThread.start();
    }

}
