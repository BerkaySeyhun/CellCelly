package com.i2icellcelly.DGW.Business;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import com.i2icellcelly.DGW.Common.DGWLogger;
import com.i2icellcelly.DGW.DataAccess.ISubscriberDal;
import com.i2icellcelly.DGW.DataAccess.HazelcastSubscriberDal;
import com.i2icellcelly.DGW.Entities.AkkaTrafficSenderActor;
import com.i2icellcelly.DGW.Entities.DGWMessage;
import com.i2icellcelly.DGW.Entities.DGWMessageFactory;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Routes the messages that is sent by the simulator to OCS. Gets the initial messages from the
 * MessageFactory class, adds the Partition Keys from the Hazelcast to the message, and sends
 * the messages to the OCS via the Akka toolkit.
 */
@Service
public class AkkaTrafficService implements ITrafficService {

    /**
     * The actor system the environment which the Akka actors are created, stored in and
     * communicated with. The actor system name is important for remote actors to connect
     * back to this host, so it is better to choose an easy name. An AkkaTrafficSenderActor
     * is created, which has the responsibility to send messages to the remote actor of the
     * OCS.
     */
    Config akkaActorConfig = ConfigFactory.parseFile(new File("actor.conf"));
    final ActorSystem akkaActorSystem = ActorSystem.create("MySystem", akkaActorConfig);
    final ActorRef publisherActor = akkaActorSystem.actorOf(AkkaTrafficSenderActor.props(), "publisher");

    /**
     * The subscriberDal has the responsibility of getting the Partition Key from Hazelcast.
     */
    ISubscriberDal subscriberDal = new HazelcastSubscriberDal();
    Integer partitionKey;

    @Override
    public void generateTraffic(JSONObject message) {
        DGWLogger.printInfoLogs("Generating new traffic with Akka");
        DGWMessage subsMessage = generateMessage(message);

        publisherActor.tell(subsMessage.toString(), publisherActor);         //The actor tells the remote OCS actor its message, and gives itself as the sender reference, so it can receive a response.
        DGWLogger.printInfoLogs("Sent message to OCS.");
    }

    /*
     * Generates an initial message from the valid JSON object parameter.
     * @requires
     *      A valid JSON object that has the desired properties. Look at the DgwApplication
     *      class for valid JSON object formats.
     * @modifies
     *      The method does not modify the JSON object parameter. After the message is created,
     *      the method gets the Partition Key from Hazelcast and adds it into the initial message.
     * @effects
     *      Returns the message with the Partition Key included. Logs the actions that are performed
     *      during its execution.
     */
    private DGWMessage generateMessage (JSONObject message){
        DGWLogger.printInfoLogs("Generating new message in business layer");
        DGWMessage subsMessage = DGWMessageFactory.create(message);

        try{
            partitionKey = Integer.parseInt(subscriberDal.getPartitionIDFromMSISDN(subsMessage.getSenderMSISDN()));
            //partitionKey = 0;
            subsMessage.set_partitionKey(partitionKey);

            DGWLogger.printInfoLogs("Message created in business layer");
        }catch (NumberFormatException e){
            DGWLogger.printWarningLogs(e.getMessage());
            e.printStackTrace();
        }
        return subsMessage;
    }
}
