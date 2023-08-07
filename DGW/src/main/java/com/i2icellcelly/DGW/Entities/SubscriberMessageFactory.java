package com.i2icellcelly.DGW.Entities;

import com.i2icellcelly.DGW.Common.DGWLogger;

import org.json.simple.JSONObject;

import java.text.SimpleDateFormat;


public class SubscriberMessageFactory {

    public static SubscriberMessage create(JSONObject message){

        DGWLogger.printInfoLogs("In the message factory.");
        SubscriberMessage subsMessage = new SubscriberMessage();

        String dateFormat ="E MMM dd HH:mm:ss zzz yyyy";
        SimpleDateFormat sDateFormat = new SimpleDateFormat(dateFormat);

        try {
            String messageType = (String) message.get("type");
            subsMessage.setType(messageType);

            JSONObject attributes = (JSONObject) message.get("attributes");

            subsMessage.setSenderMSISDN((String) attributes.get("msisdn"));
            subsMessage.setLocation((Long) attributes.get("location"));
            subsMessage.setDate(sDateFormat.parse((String) attributes.get("date")));

            switch (messageType) {
                case "data" -> {
                    subsMessage.setUsageAmount((Long) attributes.get("dataUsage"));
                    subsMessage.setRatingNumber((Long) attributes.get("rGroup"));
                }
                case "sms" -> subsMessage.setSenderMSISDN((String) attributes.get("bMsisdn"));
                case "voice" -> {
                    subsMessage.setUsageAmount((Long) attributes.get("dataUsage"));
                    subsMessage.setReceiverMSISDN((String) attributes.get("bMsisdn"));
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return subsMessage;
    }
}
