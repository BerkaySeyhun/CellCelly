### About this module
This is kafka module of CellCelly OCS project.
To run kafka on GCP you have to download and upload the 
downloaded file to the Cloud. Then you should change the
configurations.  
>[!NOTE]
> This module uses downloaded `.zip` file not the Docker image.

### Configurations
>[!IMPORTANT]
>For `server.properties` file in `config` directory you should
change the configurations as below. 

```
# The address the socket server listens on. If not configured, the host name will be equal to the value of 
# java.net.InetAddress.getCanonicalHostName(), with PLAINTEXT listener name, and port 9092.
#   FORMAT:
#     listeners = listener_name://host_name:port
#   EXAMPLE:
#     listeners = PLAINTEXT://your.host.name:9092
listeners=PLAINTEXT://<your-internal-IP>:9092

# Listener name, hostname and port the broker will advertise to clients.
# If not set, it uses the value for "listeners".
advertised.listeners=PLAINTEXT://<your-external-IP>:9092

# Maps listener names to security protocols, the default is for them to be the same. See the config documentation for more details
listener.security.protocol.map=PLAINTEXT:PLAINTEXT,EXTERNAL:PLAINTEXT,EXTERNAL:SSL,INTERNAL:PLAINTEXT,SSL:SSL,SASL_PLAINTEXT:SASL_PLAINTEXT,SASL_SSL:SASL_SSL
```

### Execution
To run Kafka on server go Kafka directory.  
Run Zookeeper:  
`bin/zookeeper-server-start.sh config/zookeeper.properties`  
Run Broker:  
`bin/kafka-server-start.sh config/server.properties`  
After that, you have zookeeper and broker up and running -for running codes 
without interruption add `nohup` at the start of command and add 
`&` at the end of the command-. Then you can add your topics and use the software.
Kafka will be hosted on `9092` port.