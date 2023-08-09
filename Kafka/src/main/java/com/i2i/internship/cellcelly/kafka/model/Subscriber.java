package com.i2i.internship.cellcelly.kafka.model;

import com.i2i.internship.cellcelly.kafka.KafkaConstants;
import com.i2i.internship.cellcelly.kafka.controller.SubscriberController;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.Properties;
public class Subscriber {
    public static Consumer <Long, UsageMessage> createConsumer(){
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstants.bootstrapServers);
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "consumerGroup");
        properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, KafkaConstants.clientID);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "com.i2i.internship.cellcelly.kafka.model.UsageMessageDeserializer");
        //properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, KafkaConstants.autoOffsetReset);

        Consumer<Long, UsageMessage> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singletonList(KafkaConstants.topicName));
        return consumer;
    }

    // Main method for test purposes
    public static void main(String[] args){
        SubscriberController.runConsumer();
    }

}
