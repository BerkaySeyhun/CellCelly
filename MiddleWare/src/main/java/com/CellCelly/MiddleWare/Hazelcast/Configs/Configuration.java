package com.CellCelly.MiddleWare.Hazelcast.Configs;

import com.hazelcast.client.config.ClientConfig;
import com.CellCelly.MiddleWare.Hazelcast.Constants.StringConstants;

public class Configuration {
    public static ClientConfig getConfig(){
        ClientConfig config = new ClientConfig();
        config.getNetworkConfig().addAddress(StringConstants.hazelcastUrl);
        return config;
    }
}
