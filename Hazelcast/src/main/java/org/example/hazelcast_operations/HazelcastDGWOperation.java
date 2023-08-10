package org.example.hazelcast_operations;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

import org.example.utils.configurations.Configuration;
import org.example.utils.constants.StringConstants;

public class HazelcastDGWOperation {
    private static final ClientConfig config = Configuration.getConfig();
    private static final HazelcastInstance hazelcast = HazelcastClient.newHazelcastClient(config);

    public static String getPartitionIdByKey(String key) {
        try{
            IMap<Object, Object> map = hazelcast.getMap(StringConstants.mapName);

            if (map.containsKey(key)) {
                String value = map.get(key).toString();
                return value;
            } else {
                return "Not found key";
            }
        }catch (Exception e){
            return e.toString();
        }finally {
            hazelcast.shutdown();
        }

    }

}
