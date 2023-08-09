package com.i2icellcelly.DGW.Business;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.i2icellcelly.DGW.Common.DGWLogger;
import com.i2icellcelly.DGW.DataAccess.ISubscriberDal;
import com.i2icellcelly.DGW.DataAccess.RestSubscriberDal;
import com.i2icellcelly.DGW.Entities.AkkaTrafficSenderActor;
import com.i2icellcelly.DGW.Entities.SubscriberMessage;
import com.i2icellcelly.DGW.Entities.SubscriberMessageFactory;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class SubscriberService implements ISubscriberService{

    Config config = ConfigFactory.parseFile(new File("actor.conf"));
    final ActorSystem system = ActorSystem.create("MySystem", config);
    final ActorRef publisher = system.actorOf(AkkaTrafficSenderActor.props(), "publisher");

    ISubscriberDal subscriberDal = new RestSubscriberDal();
    Integer partitionKey;

    @Override
    public void generateTraffic(JSONObject message) {
        DGWLogger.printInfoLogs("Generating new traffic with Akka");
        SubscriberMessage subsMessage = generateMessage(message);

        publisher.tell(subsMessage.toString(), publisher);
        DGWLogger.printInfoLogs("Sent message to OCS.");
    }

    public SubscriberMessage generateMessage (JSONObject message){
        DGWLogger.printInfoLogs("Generating new message in business layer");
        SubscriberMessage subsMessage = SubscriberMessageFactory.create(message);

        partitionKey = Integer.parseInt(subscriberDal.getPartitionIDFromMSISDN(subsMessage.getSenderMSISDN()));
        subsMessage.set_partitionKey(partitionKey);

        DGWLogger.printInfoLogs("Message created in business layer");
        return subsMessage;
    }
}
