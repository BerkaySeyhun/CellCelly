package org.example.entities.concretes;


import org.example.entities.abstracts.Entity;

import java.util.Date;

/**
 * The Sms class is the interface for creating Sms traffic requests in the JSON format.
 */
public class Sms implements Entity {
    private final int location;
    private final String msisdn;
    private final String bMsisdn;
    /**
     * "date" is the date of the communication.
     */
    private final Date date;

    /**
     * The constructor
     * @param location   The location of the communication.
     * @param msisdn     The MSISDN of the sender side.
     * @param bMsisdn    The MSISDN of the receiving side.
     */
    public Sms(int location, String msisdn, String bMsisdn) {
        this.location = location;
        this.msisdn = msisdn;
        this.bMsisdn = bMsisdn;
        this.date = new Date();
    }

    /**
     * Returns the necessary information for the DGW in a proper JSON format.
     * @return    An SMS packet in the JSON format.
     */
    @Override
    public String toString() {
        return "{" +
                "\"location\" : " + location +
                ", \"msisdn\" : \"" + msisdn + '\"' +
                ", \"bMsisdn\" : \"" + bMsisdn + '\"' +
                ", \"date\" : \"" + date + '\"' +
                '}';
    }
}
