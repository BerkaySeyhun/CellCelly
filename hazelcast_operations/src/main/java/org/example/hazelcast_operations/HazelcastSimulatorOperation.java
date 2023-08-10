package org.example.hazelcast_operations;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.example.utils.configurations.Configuration;
import org.example.utils.constants.StringConstants;

import java.util.Collection;

public class HazelcastSimulatorOperation {
    private static final ClientConfig config = Configuration.getConfig();
    private static final HazelcastInstance hazelcast = HazelcastClient.newHazelcastClient(config);

    public static Collection<Object> getAllPartitionId() {
        try {
            IMap<Object, Object> map = hazelcast.getMap(StringConstants.mapName);

            Collection<Object> allPartitionId = map.values();

            return allPartitionId;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            //hazelcast.shutdown();
        }
    }

    public static Collection<Object> getAllMsisdn(){
        try{
            IMap<Object, Object> map = hazelcast.getMap(StringConstants.mapName);
            return map.keySet();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            //hazelcast.shutdown();
        }
    }
}
