package com.i2i.internship.cellcelly.sf.mw_connect;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class MWOperations {

    private static final String baseUrl = "http://35.194.5.106:8080/api/balance";
    public static String updateSms(long custId, String msisdn, long amount){

        try {
            HttpResponse<String> response = Unirest.put(baseUrl+"/updateusersms/"+custId+"/"+msisdn+"/"+amount)
                    .asString();
            return response.getBody();
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }

    public static String updateData(long custId, String msisdn, long amount){
        try {
            HttpResponse<String> response = Unirest.put(baseUrl+"/updateuserdata/"+custId+"/"+msisdn+"/"+amount)
                    .asString();
            return response.getBody();
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }
    public static String updateMinute(long custId, String msisdn, long amount){

        try {
            HttpResponse<String> response = Unirest.put(baseUrl+"/updateuserminute/"+custId+"/"+msisdn+"/"+amount)
                    .asString();
            return response.getBody();
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }

        public static String updateWallet(long custId, String msisdn, int price){

            try {
                HttpResponse<String> response = Unirest.put(baseUrl+"/updateusermoney/"+custId+"/"+msisdn+"/"+price)
                        .asString();
                return response.getBody();
            } catch (UnirestException e) {
                throw new RuntimeException(e);
            }
    }

}
