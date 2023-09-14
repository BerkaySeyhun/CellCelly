package org.example.voltDB;

import org.voltdb.VoltTable;
import org.voltdb.client.*;

import java.io.IOException;
import java.math.BigDecimal;

public class VoltDbOperation {

    private String id;
    private int port;
    private ClientResponse response = null;
    private Client client;


    public VoltDbOperation(){

        this.id = "34.71.25.73";
        this.port = 32768;

        try {
            this.client=getClientVoltDB();

        } catch (IOException | ProcCallException e) {

            e.printStackTrace();
        }
    }


    public Client getClientVoltDB() throws IOException, ProcCallException {

        ClientConfig config = new ClientConfig ();
        Client client = ClientFactory.createClient(config);
        client.createConnection (id, port);

        return  client;
    }


    public String getPackageName(String MSISDN){

        try {

            response = client.callProcedure("getPackage", MSISDN);

        } catch (IOException | ProcCallException e) {

            e.printStackTrace();
        }

        VoltTable table2 =response.getResults()[0];
        table2.advanceRow();
        return table2.getString(1);
    }

    public long getUidByMSISDN(String MSISDN){

        try {

            response = client.callProcedure("getMSISDNWithId",MSISDN);

        } catch (IOException | ProcCallException e) {
            e.printStackTrace();
        }

        VoltTable table = response.getResults()[0];
        table.advanceRow();
        return table.getLong(0);
    }


    public BigDecimal getVoiceBalance(String MSISDN){

        int uid= (int) getUidByMSISDN(MSISDN);


        try {
            response = client.callProcedure("ShowPackageAmountMinutes",uid);

        } catch (IOException | ProcCallException e) {

            throw new RuntimeException(e);
        }
        VoltTable table = response.getResults()[0];
        table.advanceRow();
        table.advanceRow();
        return table.getDecimalAsBigDecimal(0);
    }


    public BigDecimal getSMSBalance(String MSISDN){


        try {
            long uid= getUidByMSISDN(MSISDN);

            response = client.callProcedure("ShowPackageAmountSMS",uid);

        } catch (IOException | ProcCallException e) {

            throw new RuntimeException(e);
        }
        VoltTable table = response.getResults()[0];
        table.advanceRow();
        table.advanceRow();
        return table.getDecimalAsBigDecimal(0);
    }


    public BigDecimal getDataBalance(String MSISDN){


        try {
            long uid= (int) getUidByMSISDN(MSISDN);

            response = client.callProcedure("ShowPackageAmountData",uid);

        } catch (IOException | ProcCallException e) {

            throw new RuntimeException(e);
        }
        VoltTable table = response.getResults()[0];
        table.advanceRow();
        table.advanceRow();
        return table.getDecimalAsBigDecimal(0);
    }


    public BigDecimal getSubscriberBalance(String MSISDN){

        try {
            int uid= (int) getUidByMSISDN(MSISDN);

            response = client.callProcedure("ShowBalanceMoney",uid);

        } catch (IOException | ProcCallException e) {

            throw new RuntimeException(e);
        }
        VoltTable table = response.getResults()[0];
        table.advanceRow();
        table.advanceRow();
        return table.getDecimalAsBigDecimal(0);
    }


    public void sendSubscriberBalance(String MSISDN, int balance){

        try {
            int uid= (int) getUidByMSISDN(MSISDN);

            client.callProcedure("UpdateBalanceMoney",uid, balance);

        } catch (IOException | ProcCallException e) {

            throw new RuntimeException(e);
        }
    }


    public void sendVoiceAmount(String MSISDN, int usedAmount){

        try {
            int uid= (int) getUidByMSISDN(MSISDN);

            client.callProcedure("UpdateBalanceMinutes",uid, usedAmount);

        } catch (IOException | ProcCallException e) {
            e.printStackTrace();
        }
    }


    public void sendSmsAmount(String MSISDN, int usedAmount){

        try {
            int uid= (int) getUidByMSISDN(MSISDN);

            client.callProcedure("UpdatEBalanceSMS",uid, usedAmount);

        } catch (IOException | ProcCallException e) {
            e.printStackTrace();
        }
    }


    public void sendGbAmount(String MSISDN, int usedAmount){

        try {
            int uid= (int) getUidByMSISDN(MSISDN);

            client.callProcedure("UpdateBalanceData",uid, usedAmount );

        } catch (IOException | ProcCallException e) {
            e.printStackTrace();
        }
    }


}
