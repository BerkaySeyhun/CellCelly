package com.i2i.internship.cellcelly.kafka.model;

import com.i2i.internship.cellcelly.kafka.KafkaConstants;
import com.i2i.internship.cellcelly.kafka.controller.PublisherController;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;

import java.io.Serializable;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class Publisher {
    public static Producer<Long, Serializable> createProducer() {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstants.bootstrapServers);
        properties.setProperty(ProducerConfig.CLIENT_ID_CONFIG, KafkaConstants.clientID);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "com.i2i.internship.cellcelly.kafka.model.UsageMessageSerializer");
        properties.setProperty(ProducerConfig.MAX_BLOCK_MS_CONFIG, "30000");
        properties.setProperty(ProducerConfig.ACKS_CONFIG, "all");

        return new KafkaProducer<>(properties);
    }

    // Main method for test purposes
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        for (int i = 0; true; i++) {
            UsageMessage message = new UsageMessage();
            message.setMsisdn("5321234567");
            message.setPrice(100);
            message.setSubscriberID(i);
            message.setUsedAmount(i * 10);
            message.setUsedService("data");

            PublisherController.runProducer(message);

            Thread.sleep(1000);
        }
    }
}
