package org.example.entities.concretes;

import org.example.entities.abstracts.Entity;

import java.util.Date;

/**
 * The Data class is the interface for creating Data traffic requests in the JSON format.
 */
public class Data implements Entity {
    private final int location;
    private final String msisdn;
    private final int dataUsage;
    private final int rGroup;
    /**
     * "date" is the date of the communication.
     */
    private final Date date;

    /**
     * The constructor.
     * @param location    Location of the sender side.
     * @param msisdn      MSISDN of the sender side.
     * @param dataUsage   Amount of data used MBs.
     * @param rGroup      The rating group that will be used by the OCS to charge the customer.
     */
    public Data(int location, String  msisdn,int dataUsage, int rGroup){
        this.location = location;
        this.msisdn = msisdn;
        this.rGroup = rGroup;
        this.dataUsage = dataUsage;
        this.date = new Date();
    }

    /**
     * Returns the necessary information for the DGW in a proper JSON format.
     * @return    A Data packet in the JSON format.
     */
    @Override
    public String toString() {
        return "{" +
                "\"location\" : " + location +
                ", \"msisdn\" : \"" + msisdn + '\"' +
                ", \"dataUsage\" : " + dataUsage +
                ", \"rGroup\" : " + rGroup +
                ", \"date\" : \"" + date + '\"' +
                '}';
    }
}
