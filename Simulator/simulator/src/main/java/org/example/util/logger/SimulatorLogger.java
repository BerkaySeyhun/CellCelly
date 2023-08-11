package org.example.util.logger;

import java.util.Date;

public class SimulatorLogger {
    public static void println(String message){
        Date date = new Date();
        System.out.println(date + " "+ message);
    }

}
