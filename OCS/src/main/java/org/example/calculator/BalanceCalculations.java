package org.example.calculator;

import org.example.kafka.KafkaOperator;
import org.example.requestMessage.AkkaRequestMessage;
import org.example.voltDB.VoltDbOperation;

import java.math.BigDecimal;

public class BalanceCalculations {

    VoltDbOperation voltOperation = new VoltDbOperation();
    KafkaOperator kafkaOperator = new KafkaOperator();


    public void calculateVoiceRequest(AkkaRequestMessage requestMessage){
        System.out.println("VOICE Request Calculating ...");

        int requestUsageAmount = requestMessage.getUsageAmount();
        double totalPrice = requestMessage.getTotalUsagePrice();

        BigDecimal userVoiceBalance = voltOperation.getVoiceBalance(requestMessage.getSenderMSISDN());
        BigDecimal userWalletBalance = voltOperation.getSubscriberBalance(requestMessage.getSenderMSISDN());
        long uID = voltOperation.getUidByMSISDN(requestMessage.getSenderMSISDN());

        System.out.println("User ID: "+(int)uID);
        System.out.println("Request Amount: "+requestUsageAmount);
        System.out.println("Total Price: "+(int) totalPrice);
        System.out.println("Voice Balance: "+userVoiceBalance.intValueExact());
        System.out.println("Wallet Balance: "+userWalletBalance.intValueExact());

        if(userVoiceBalance.intValueExact() <= 0){

            if(userWalletBalance.intValueExact() <= 0){

                System.out.println("No Sufficient VOICE and WALLET Balance");

            } else if(userWalletBalance.intValueExact() >= totalPrice){

                System.out.println("VOICE Request * WALLET * Condition");


                System.out.println("VOICE request Used ** WALLET ** Balance");
                voltOperation.sendSubscriberBalance(requestMessage.getSenderMSISDN(), -((int) totalPrice));
                kafkaOperator.sendKafkaWalletMessage(requestMessage.getSenderMSISDN(), (int) uID, (int)totalPrice);
                System.out.println("*** DB SENT ***");

            } else if(userWalletBalance.intValueExact() < totalPrice){

                System.out.println("No Sufficient WALLET Balance");

            }
        } else if(userVoiceBalance.intValueExact() >= requestUsageAmount){

            System.out.println("VOICE Request * NORMAL * Condition");


            System.out.println("VOICE request Used ** BALANCE **  Used");
            voltOperation.sendVoiceAmount(requestMessage.getSenderMSISDN(), requestUsageAmount);
            kafkaOperator.sendKafkaUsageMessage(requestMessage.getType(), requestMessage.getSenderMSISDN(), (int) uID, requestMessage.getUsageAmount());
            System.out.println("*** DB SENT ***");

        } else if(userVoiceBalance.intValueExact() < requestUsageAmount){

            System.out.println("VOICE Request * CALCULATED * Condition");


            requestMessage.setUsageAmount(requestUsageAmount - userVoiceBalance.intValueExact());
            requestMessage.calculateTotalPrice();

            System.out.println("Voice Request Used ** CALCULATED WALLET ** Balance");
            voltOperation.sendSubscriberBalance(requestMessage.getSenderMSISDN(), -((int) requestMessage.getTotalUsagePrice()));
            kafkaOperator.sendKafkaWalletMessage(requestMessage.getSenderMSISDN(), (int) uID, (int) requestMessage.getTotalUsagePrice());
            System.out.println("*** DB SENT ***");


            int remainingVoiceAmount =  userVoiceBalance.intValueExact();


            System.out.println("Voice Request Used ** CALCULATED VOICE ** Balance");
            voltOperation.sendVoiceAmount(requestMessage.getSenderMSISDN(), remainingVoiceAmount);
            kafkaOperator.sendKafkaUsageMessage(requestMessage.getType(), requestMessage.getSenderMSISDN(), (int) uID, userVoiceBalance.intValueExact());
            System.out.println("*** DB SENT ***");
        }
    }

    public void calculateSMSRequest(AkkaRequestMessage requestMessage){
        System.out.println("SMS Request Calculating ...");

        int requestUsageAmount = requestMessage.getUsageAmount();
        double totalPrice = requestMessage.getTotalUsagePrice();

        BigDecimal userSMSBalance = voltOperation.getSMSBalance(requestMessage.getSenderMSISDN());
        BigDecimal userWalletBalance = voltOperation.getSubscriberBalance(requestMessage.getSenderMSISDN());
        long uID = voltOperation.getUidByMSISDN(requestMessage.getSenderMSISDN());

        System.out.println("User ID: "+(int)uID);
        System.out.println("Request Amount: "+requestUsageAmount);
        System.out.println("Total Price: "+(int) totalPrice);
        System.out.println("SMS Balance: "+userSMSBalance.intValueExact());
        System.out.println("Wallet Balance: "+userWalletBalance.intValueExact());

        if(userSMSBalance.intValueExact() <= 0){
            if(userWalletBalance.intValueExact() <= 0){

                System.out.println("No Sufficient WALLET and SMS Balance");

            } else if (userWalletBalance.intValueExact() >= totalPrice) {
                System.out.println("SMS Request * WALLET * Condition");

                System.out.println("SMS request Used ** WALLET ** Balance");
                voltOperation.sendSubscriberBalance(requestMessage.getSenderMSISDN(), -((int) totalPrice));
                kafkaOperator.sendKafkaWalletMessage(requestMessage.getSenderMSISDN(), (int) uID, (int)totalPrice);
                System.out.println("*** DB SENT ***");

            }
        } else {//user has sms balance
            System.out.println("SMS Request * NORMAL * Condition");

            System.out.println("SMS request Used ** SMS ** Balance");
            voltOperation.sendSmsAmount(requestMessage.getSenderMSISDN(), requestMessage.getUsageAmount());
            kafkaOperator.sendKafkaUsageMessage(requestMessage.getType(), requestMessage.getSenderMSISDN(), (int) uID, requestUsageAmount);
            System.out.println("*** DB SENT ***");

        }

    }

    public void calculateDataRequest(AkkaRequestMessage requestMessage){
        System.out.println("DATA Request Calculating ...");

        int requestUsageAmount = requestMessage.getUsageAmount();
        double totalPrice = requestMessage.getTotalUsagePrice();


        BigDecimal userDataBalance = voltOperation.getDataBalance(requestMessage.getSenderMSISDN());
        BigDecimal userWalletBalance = voltOperation.getSubscriberBalance(requestMessage.getSenderMSISDN());
        long uID = voltOperation.getUidByMSISDN(requestMessage.getSenderMSISDN());

        System.out.println("User ID: "+(int)uID);
        System.out.println("Request Amount: "+requestUsageAmount);
        System.out.println("Total Price: "+(int) totalPrice);
        System.out.println("DATA Balance: "+userDataBalance.intValueExact());
        System.out.println("Wallet Balance: "+userWalletBalance.intValueExact());

        if(userDataBalance.intValueExact() <= 0){

            if(userWalletBalance.intValueExact() <= 0){

                System.out.println("No Sufficient VOICE and WALLET Balance");

            } else if(userWalletBalance.intValueExact() >= totalPrice){

                System.out.println("DATA request Used ** BALANCE **  Used");
                voltOperation.sendSubscriberBalance(requestMessage.getSenderMSISDN(), -((int) totalPrice));
                kafkaOperator.sendKafkaWalletMessage(requestMessage.getSenderMSISDN(), (int) uID, (int)totalPrice);
                System.out.println("*** DB SENT ***");

            } else if(userWalletBalance.intValueExact() < totalPrice){

                System.out.println("No Sufficient WALLET Balance");

            }
        } else if(userDataBalance.intValueExact() >= requestUsageAmount){
            System.out.println("DATA Request * NORMAL * Condition");

            voltOperation.sendGbAmount(requestMessage.getSenderMSISDN(), requestUsageAmount);
            kafkaOperator.sendKafkaUsageMessage(requestMessage.getType(), requestMessage.getSenderMSISDN(), (int) uID, requestMessage.getUsageAmount());
            System.out.println("*** DB SENT ***");

        } else if(userDataBalance.intValueExact() < requestUsageAmount){

            requestMessage.setUsageAmount(requestUsageAmount - userDataBalance.intValueExact());
            System.out.println("New Usage Amount: "+ (requestUsageAmount - userDataBalance.intValueExact()));
            requestMessage.calculateTotalPrice();
            System.out.println("Calculated Total Price: "+ requestMessage.getTotalUsagePrice());

            System.out.println("DATA Request Used ** CALCULATED WALLET ** Balance");
            voltOperation.sendSubscriberBalance(requestMessage.getSenderMSISDN(), -((int) requestMessage.getTotalUsagePrice()));
            kafkaOperator.sendKafkaWalletMessage(requestMessage.getSenderMSISDN(), (int) uID, (int) requestMessage.getTotalUsagePrice());
            System.out.println("*** DB SENT ***");


            int remainingDataAmount =  userDataBalance.intValueExact();
            System.out.println("RemainingDataAmount: "+ remainingDataAmount);

            System.out.println("DATA Request Used ** CALCULATED VOICE ** Balance");
            voltOperation.sendVoiceAmount(requestMessage.getSenderMSISDN(), remainingDataAmount);
            kafkaOperator.sendKafkaUsageMessage(requestMessage.getType(), requestMessage.getSenderMSISDN(), (int) uID, userDataBalance.intValueExact());
            System.out.println("*** DB SENT ***");
        }
    }

}
