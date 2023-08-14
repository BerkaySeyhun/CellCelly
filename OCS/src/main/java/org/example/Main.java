package org.example;


import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.example.akka.AkkaListener;

import java.io.File;


public class Main {
    public static void main(String[] args) throws InterruptedException {

        Config config = ConfigFactory.parseFile(new File("akka-listener-config.conf"));
        final ActorSystem system = ActorSystem.create("MySystem", config);

        final ActorRef listener = system.actorOf(AkkaListener.props(), "listener");

    }
}