package com.i2i.internship.cellcelly.kafka.controller;

import com.i2i.internship.cellcelly.kafka.KafkaConstants;
import com.i2i.internship.cellcelly.kafka.model.Publisher;
import com.i2i.internship.cellcelly.kafka.model.UsageMessage;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.time.Duration;
import java.util.concurrent.ExecutionException;

public class PublisherController {
    private static Logger logger = LogManager.getLogger(PublisherController.class);


    public static void runProducer(UsageMessage msg) throws ExecutionException, InterruptedException {

        Producer producer = Publisher.createProducer();

        logger.info("Producer created.");
        try{
            ProducerRecord<Long, Serializable> record = new ProducerRecord<>(KafkaConstants.topicName, msg);
            producer.send(record, (metadata, exception) -> {
                if (metadata != null) {
                    logger.info("Message sent: " + msg +
                            ", Partition: " + metadata.partition() +
                            ", Offset: " + metadata.offset());
                } else {
                    logger.error("Error while sending message: " + exception.getMessage());
                }
            });
        } catch (Exception e){
            logger.error(e);
        }



    }

}