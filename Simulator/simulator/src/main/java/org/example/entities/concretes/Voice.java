package org.example.entities.concretes;

import org.example.entities.abstracts.Entity;

import java.util.Date;

/**
 * The Voice class is the interface for creating Voice traffic requests in the JSON format.
 */
public class Voice implements Entity {
    private final int location;
    private final String msisdn;
    private final int duration;
    private final String bMsisdn;
    /**
     * "date" is the date of the communication.
     */
    private final Date date;

    /**
     * The constructor.
     * @param location    The location of the communication.
     * @param msisdn      The MSISDN of the sender side.
     * @param duration    The duration of the voice call in minutes.
     * @param bMsisdn     The MSISDN of the receiving side.
     */
    public Voice(int location, String msisdn, int duration, String bMsisdn){
        this.location = location;
        this.msisdn = msisdn;
        this.duration = duration;
        this.bMsisdn = bMsisdn;
        this.date = new Date();
    }

    /**
     * Returns the necessary information for the DGW in a proper JSON format.
     * @return     A Voice packet in the JSON format.
     */
    @Override
    public String toString() {
        return "{" +
                "\"location\" : " + location +
                ", \"msisdn\" : \"" + msisdn + '\"' +
                ", \"duration\" : " + duration +
                ", \"bMsisdn\" : \"" + bMsisdn + '\"' +
                ", \"date\" : \""+ date + '\"' +
                '}';
    }
}
