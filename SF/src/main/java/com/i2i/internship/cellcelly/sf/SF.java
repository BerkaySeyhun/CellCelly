package com.i2i.internship.cellcelly.sf;

import com.i2i.internship.cellcelly.kafka.model.Subscriber;
import com.i2i.internship.cellcelly.kafka.model.UsageMessage;
import com.i2i.internship.cellcelly.sf.mw_connect.MWOperations;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

import java.time.Duration;

public class SF {
    //private static Logger logger = LogManager.getLogger(SF.class);

    public static void main(String[] args) throws SQLException {
        Consumer<Long, UsageMessage> consumer = Subscriber.createConsumer();


        while (true) {
            ConsumerRecords<Long, UsageMessage> consumerRecords = consumer.poll(Duration.ofSeconds(1));

            if (consumerRecords.count() == 0) {
               // logger.info("No message to read");
                continue;
            }
            for (ConsumerRecord<Long, UsageMessage> record : consumerRecords) {
               // logger.info("Message received");
                //logger.info(
                //       record.toString()
                //);
                UsageMessage msg = record.value();
                String msisdn =msg.getMsisdn();
                Long usedAmount= msg.getUsedAmount();
                String usedService= msg.getUsedService();
                int price =msg.getPrice();
                int subscriberID= msg.getSubscriberID();

                String response="";
                if (usedService.equals("data")){
                     response = MWOperations.updateData(subscriberID,msisdn,usedAmount);
                }if(usedService.equals("sms")){
                     response = MWOperations.updateSms(subscriberID,msisdn,usedAmount);
                }if(usedService.equals("voice")){
                     response =  MWOperations.updateMinute(subscriberID,msisdn,usedAmount);
                }if(usedService.equals("wallet")){
                    response =  MWOperations.updateWallet(subscriberID,msisdn,price);
                }
                System.out.println("RESPONSE : " + response);

                System.out.println(subscriberID);
                System.out.println(msisdn);
                System.out.println(usedAmount);
                System.out.println(usedService);
                System.out.println(price);
            }

            consumer.commitAsync();
        }
    }
}
