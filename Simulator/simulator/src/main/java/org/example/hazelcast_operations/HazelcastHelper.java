package org.example.hazelcast_operations;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.example.util.logger.SimulatorLogger;

import java.util.Collection;
import java.util.Random;

public class HazelcastHelper {

    public static String getRandomSubscriber(){
        try {
            HttpResponse<String> response = Unirest.get("http://34.133.255.110:8095/api/hazelcast/getRandomMsisdn")
                    .asString();
            return response.getBody();
        } catch (Exception e){
            SimulatorLogger.println(e.toString());
            e.printStackTrace();
        }
        return "Not successfully operation";
    }

}
