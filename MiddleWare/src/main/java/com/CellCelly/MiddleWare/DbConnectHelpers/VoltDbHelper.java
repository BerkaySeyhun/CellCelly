
package com.CellCelly.MiddleWare.DbConnectHelpers;


import org.voltdb.client.Client;
import org.voltdb.client.ClientConfig;
import org.voltdb.client.ClientFactory;
import java.io.IOException;

public class VoltDbHelper {

    private final String dbUrl = "34.71.25.73";
    private final int port = 32768;
    public Client client() throws IOException {
        Client client;
        ClientConfig config;
        config = new ClientConfig();
        client = ClientFactory.createClient(config);
        client.createConnection(dbUrl, port);
        return client;

    }
}