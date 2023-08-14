package com.CellCelly.MiddleWare.Hazelcast.MwOperations;

import com.CellCelly.MiddleWare.Hazelcast.Configs.Configuration;
import com.CellCelly.MiddleWare.Hazelcast.Constants.StringConstants;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;



public class HazelcastMWOperation {

    private static ClientConfig config = Configuration.getConfig();
    private static HazelcastInstance hazelcast = HazelcastClient.newHazelcastClient(config);
    public static String put(String key, String value){
        try{
            IMap<Object, Object> map = hazelcast.getMap(StringConstants.mapName);
            map.put(key,value);
            return "Put operation is successful";
        }catch (Exception e){
            e.printStackTrace();
            return "Put operation is not successful";
        }finally {
            hazelcast.shutdown();
        }
    }

    public static String remove(String key){
        try{
            IMap<Object, Object> map = hazelcast.getMap(StringConstants.mapName);
            if (map.containsKey(key)){
                map.remove(key);
                return "Remove operation is successful";
            }
            return "Not found key";
        }catch (Exception e){
            e.printStackTrace();
            return "Remove operation is not successful";
        }finally {
            hazelcast.shutdown();
        }
    }

}
