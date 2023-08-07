package com.i2icellcelly.DGW.Entities;

import akka.actor.AbstractActor;
import akka.actor.Props;
import com.i2icellcelly.DGW.Common.GlobalData;

public class AkkaTrafficSenderActor extends AbstractActor{

    public static Props props() {
        return Props.create(AkkaTrafficSenderActor.class, AkkaTrafficSenderActor::new);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, message -> {
                    getContext().actorSelection(GlobalData.OCS_AKKA_ADDRESS).tell(message, getSelf());
                })
                .build();
    }

}