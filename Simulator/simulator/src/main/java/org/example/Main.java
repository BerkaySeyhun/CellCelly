package org.example;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.example.entities.abstracts.Entity;
import org.example.entities.concretes.Data;
import org.example.entities.concretes.Sms;
import org.example.entities.concretes.Voice;
import org.example.hazelcast_operations.HazelcastSimulatorOperation;
import org.example.repo.RandomValues;
import org.example.util.logger.SimulatorLogger;

import java.util.Collection;
import java.util.Random;

public class Main {
    private static Random random = new Random();

    public static void main(String[] args) throws InterruptedException {

        while (true) {
            int requestIndex = random.nextInt(3);
            if (requestIndex == 0) {
                SimulatorLogger.println("data");
                Data data = getDataRequest();
                requestDGW("data",data);
            }
            else if (requestIndex == 1) {
                SimulatorLogger.println("voice");
                Voice voice = getVoiceRequest();
                requestDGW("voice", voice);
            }
            else {
                SimulatorLogger.println("sms");
                Sms sms = getSmsRequest();
                requestDGW("sms", sms);
            }
            Thread.sleep(4000);
        }
    }

    public static Data getDataRequest() {
        String msisdn = RandomValues.getRandomMsisdn();
        int dataMb = RandomValues.getRandomDataUsageMB();
        int location = RandomValues.getRandomLocation();
        int rGroup = RandomValues.getRandomBNumber();
        SimulatorLogger.println(msisdn + " using data");
        return new Data(location, msisdn, dataMb, rGroup);
    }

    public static Sms getSmsRequest() {
        String msisdn = RandomValues.getRandomMsisdn();
        int location = RandomValues.getRandomLocation();
        String bMsisdn;
        do {
            bMsisdn = RandomValues.getRandomMsisdn();
        } while (msisdn.equals(bMsisdn));
        if (location == 49){
            bMsisdn = 90 + bMsisdn;
        }
        else{
            bMsisdn = RandomValues.getRandomLocation() + bMsisdn;
        }
        SimulatorLogger.println("from sms : " + msisdn);
        SimulatorLogger.println("to : " + bMsisdn);
        return new Sms(location, msisdn, bMsisdn);
    }

    public static Voice getVoiceRequest() {
        String msisdn = RandomValues.getRandomMsisdn();
        int location = RandomValues.getRandomLocation();
        int duration = RandomValues.getRandomDuration();
        String bMsisdn;
        do {
            bMsisdn = RandomValues.getRandomMsisdn();
        } while (msisdn.equals(bMsisdn));
        if (location == 49){
            bMsisdn = 90 + bMsisdn;
        }
        else{
            bMsisdn = RandomValues.getRandomLocation() + bMsisdn;
        }
        SimulatorLogger.println("from call : " + msisdn);
        SimulatorLogger.println("to : " + bMsisdn);
        return new Voice(location, msisdn, duration, bMsisdn);
    }

    public static void requestDGW(String type, Entity entity) {
        try {
            System.out.println("ENTITY : "+entity.toString());
            HttpResponse<String> addSubscriberResponse = Unirest.post("http://34.105.150.121:8080/api/generateTraffic")
                    .header("Content-Type", "application/json")
                    .body("{\"type\" : \"" +
                            type +
                            "\", \"attributes\" : " +
                            entity.toString() +
                            "}")
                    .asString();
                System.out.println("STATUS CODE : " +addSubscriberResponse.getStatus());
            if (addSubscriberResponse.getStatus() != 200) {
                System.out.println(addSubscriberResponse.getStatus());
                System.out.println("Something went wrong.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("HATA VAR");
        }
    }
}