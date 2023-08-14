package org.example.kafka;

import com.i2i.internship.cellcelly.kafka.controller.PublisherController;
import com.i2i.internship.cellcelly.kafka.model.UsageMessage;

import java.util.concurrent.ExecutionException;

public class KafkaOperator {

    public void sendKafkaUsageMessage(String type, String MSISDN, Integer subID, Integer usageAmount){

        UsageMessage message = new UsageMessage();


        message.setUsedService(type);
        message.setMsisdn(MSISDN);
        message.setSubscriberID(subID);
        message.setUsedAmount(usageAmount);
        //old project variable set 0
        message.setPrice(0);

        try{
            PublisherController.runProducer(message);
            Thread.sleep(1000);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendKafkaWalletMessage(String MSISDN, Integer subID, Integer usageTotalPrice){
        UsageMessage message = new UsageMessage();


        message.setUsedService("wallet");
        message.setMsisdn(MSISDN);
        message.setSubscriberID(subID);
        message.setUsedAmount(0);
        message.setPrice(usageTotalPrice);

        try{
            PublisherController.runProducer(message);
            Thread.sleep(1000);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
