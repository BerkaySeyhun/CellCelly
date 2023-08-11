package org.example.util.configurations;

import com.hazelcast.client.config.ClientConfig;
import org.example.util.constants.StringConstants;

public class Configuration {
    public static ClientConfig getConfig(){
        ClientConfig config = new ClientConfig();
        config.getNetworkConfig().addAddress(StringConstants.hazelcastUrl).setSmartRouting(false);
        return config;
    }
}
