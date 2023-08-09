package com.i2icellcelly.DGW.Common;

import com.hazelcast.client.config.ClientConfig;

public class HazelConfiguration {
    public static ClientConfig getConfig(){
        ClientConfig config = new ClientConfig();
        config.getNetworkConfig().addAddress(GlobalData.HAZEL_ADDRESS);
        return config;
    }
}
