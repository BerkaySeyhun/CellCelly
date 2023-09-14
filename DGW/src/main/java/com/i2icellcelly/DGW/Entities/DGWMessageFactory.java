package com.i2icellcelly.DGW.Entities;

import com.i2icellcelly.DGW.Common.DGWLogger;

import org.json.simple.JSONObject;

import java.text.SimpleDateFormat;

/**
 * Creates the messages that will be sent to the OCS.
 */
public class DGWMessageFactory {

    /**
     * The creator method for the factory class. Parses the JSON object from the simulator and
     * creates the DGWMessage object accordingly.
     * @param message    The method takes a valid JSON object as input. The valid forms of the
     *                   JSON object is exampled in the DgwApplication(readme) class.
     * @return           Returns a DGWMessage object that has all the necessary information
     *                   for the OCS to calculate the communication costs and do the balance ops.
     */
    public static DGWMessage create(JSONObject message){

        DGWLogger.printInfoLogs("In the message factory.");
        DGWMessage subsMessage = new DGWMessage();

        String dateFormat ="E MMM dd HH:mm:ss zzz yyyy";                 //The format for the date field of the object.
        SimpleDateFormat sDateFormat = new SimpleDateFormat(dateFormat);

        try {
            String messageType = (String) message.get("type");                                         //Message type.
            subsMessage.setType(messageType);

            JSONObject attributes = (JSONObject) message.get("attributes");                            //Type-specific info.

            subsMessage.setSenderMSISDN((String) attributes.get("msisdn"));                            //MSISDN of the customer.
            subsMessage.setLocation((Long) attributes.get("location"));                                //Location of the customer.
            subsMessage.setDate(sDateFormat.parse((String) attributes.get("date")));                   //Date of the communication. Used for checking if the

            switch (messageType) {
                case "data" -> {
                    subsMessage.setUsageAmount((Long) attributes.get("dataUsage"));                    //Data usage amount
                    subsMessage.setRatingNumber((Long) attributes.get("rGroup"));                      //The rating group, plays a role in calculating the cost of the communication. 0 or 10.
                }
                case "sms" -> {
                    subsMessage.setReceiverMSISDN((String) attributes.get("bMsisdn"));                 //MSISDN of the sender.
                    subsMessage.setUsageAmount(Long.parseLong("1"));                                //SMS usage, always 1.
                }
                case "voice" -> {
                    subsMessage.setUsageAmount((Long) attributes.get("duration"));                     //The duration of the call in minutes.
                    subsMessage.setReceiverMSISDN((String) attributes.get("bMsisdn"));                 //MSISDN of the receiving party.
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return subsMessage;
    }
}
