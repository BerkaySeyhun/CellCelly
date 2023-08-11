package org.example.repo;

import org.example.hazelcast_operations.HazelcastSimulatorOperation;

import java.util.*;

public class RandomValues {
    private static  Random random = new Random();
    private static List<Integer> locations = new ArrayList<>(Arrays.asList(90,49));


    public static int getRandomDataUsageMB(){
        return random.nextInt(750);
    }

    public static int getRandomDuration(){
        return random.nextInt(350);
    }

    public static int getRandomLocation(){
        int index = random.nextInt(2);
        return locations.get(index);
    }

    public static int getRandomBNumber(){
        int randomNumber = random.nextInt(2);
        if (randomNumber == 0){return 0;}
        else {return 10;}
    }

    public static String getRandomMsisdn(){
        Collection<Object> allMsisdn = HazelcastSimulatorOperation.getAllMsisdn();

        int index = random.nextInt(allMsisdn.size());
        return allMsisdn.toArray()[index].toString();
    }

}
