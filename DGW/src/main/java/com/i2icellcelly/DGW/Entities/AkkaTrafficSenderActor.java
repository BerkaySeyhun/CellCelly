package com.i2icellcelly.DGW.Entities;

import akka.actor.AbstractActor;
import akka.actor.Props;

import com.i2icellcelly.DGW.Common.GlobalData;

/**
 * The Akka actor that will send the messages to the OCS.
 */
public class AkkaTrafficSenderActor extends AbstractActor{

    /**
     * Creates a new instance of an AkkaTrafficSenderActor.
     * @return     Returns a Props object. This object acts as a configuration object for creating Actors.
     *             In this instance, the props object creates an AkkaTrafficSenderActor.
     */
    public static Props props() {
        return Props.create(AkkaTrafficSenderActor.class, AkkaTrafficSenderActor::new);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, message -> getContext().actorSelection(GlobalData.OCS_AKKA_ADDRESS).tell(message, getSelf()))
                .build();
    }

}