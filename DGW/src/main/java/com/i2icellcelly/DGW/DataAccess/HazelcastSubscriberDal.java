package com.i2icellcelly.DGW.DataAccess;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

import com.i2icellcelly.DGW.Common.GlobalData;
import com.i2icellcelly.DGW.Common.HazelConfiguration;

import org.springframework.stereotype.Repository;

@Repository
public class HazelcastSubscriberDal implements ISubscriberDal{

    /*
     * The configuration and the client for the Hazelcast server.
     */
    private static final ClientConfig HZ_CLIENT_CONFIG = HazelConfiguration.getConfig();
    private static final HazelcastInstance HAZELCAST_CLIENT = HazelcastClient.newHazelcastClient(HZ_CLIENT_CONFIG);

    @Override
    public String getPartitionIDFromMSISDN(String key) throws NumberFormatException{
        try{
            IMap<Object, Object> map = HAZELCAST_CLIENT.getMap(GlobalData.MAP_NAME);

            if (map.containsKey(key)) {
                return map.get(key).toString();
            } else {
                throw new NumberFormatException("Key not found.");
            }
        }catch (Exception e){
            return e.toString();
        }
    }
}
