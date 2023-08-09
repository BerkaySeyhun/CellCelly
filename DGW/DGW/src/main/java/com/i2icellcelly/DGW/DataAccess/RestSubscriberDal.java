package com.i2icellcelly.DGW.DataAccess;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.i2icellcelly.DGW.Common.GlobalData;
import com.i2icellcelly.DGW.Common.HazelConfiguration;
import org.springframework.stereotype.Repository;

@Repository
public class RestSubscriberDal implements ISubscriberDal{

    private static final ClientConfig config = HazelConfiguration.getConfig();
    private static final HazelcastInstance hazelcast = HazelcastClient.newHazelcastClient(config);

    @Override
    public String getPartitionIDFromMSISDN(String key) {
        try{
            IMap<Object, Object> map = hazelcast.getMap(GlobalData.MAP_NAME);

            if (map.containsKey(key)) {
                return map.get(key).toString();
            } else {
                return "Key not found";
            }
        }catch (Exception e){
            return e.toString();
        }
    }
}
