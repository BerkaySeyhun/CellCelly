package org.example.utils.configurations;

import com.hazelcast.client.config.ClientConfig;
import org.example.utils.constants.StringConstants;

public class Configuration {
    public static ClientConfig getConfig(){
        ClientConfig config = new ClientConfig();
        config.getNetworkConfig().addAddress(StringConstants.hazelcastUrl);
        return config;
    }
}
