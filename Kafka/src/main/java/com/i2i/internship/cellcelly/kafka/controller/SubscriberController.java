package com.i2i.internship.cellcelly.kafka.controller;

import com.i2i.internship.cellcelly.kafka.model.UsageMessage;
import com.i2i.internship.cellcelly.kafka.model.Subscriber;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;


// This class should be in SF I implemented in here only for test purposes.
public class SubscriberController {
    private static Logger logger = LogManager.getLogger(SubscriberController.class);

    public static void runConsumer() {
        Consumer<Long, UsageMessage> consumer = Subscriber.createConsumer();

        while (true) {
            ConsumerRecords<Long, UsageMessage> consumerRecords = consumer.poll(Duration.ofSeconds(1));

            if (consumerRecords.count() == 0) {
                logger.info("No message to read");
                continue;
            }
            for (ConsumerRecord<Long, UsageMessage> record : consumerRecords) {
                logger.info("Message received");
                logger.info(
                        record.toString()
                );
                UsageMessage msg = record.value();
                String msisdn =msg.getMsisdn();
                Long usedAmount= msg.getUsedAmount();
                String usedService= msg.getUsedService();
                int price =msg.getPrice();
                int subscriberID= msg.getSubscriberID();

                System.out.println(msisdn);
                System.out.println(usedAmount);
                System.out.println(usedService);
                System.out.println(price);
                System.out.println(subscriberID);
            }

            consumer.commitAsync();
        }
        //consumer.close();
    }
}
