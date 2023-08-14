
package com.CellCelly.MiddleWare.DataAccesLayers;

import com.CellCelly.MiddleWare.DbConnectHelpers.OracleDbHelper;
import com.CellCelly.MiddleWare.DbConnectHelpers.VoltDbHelper;
import com.CellCelly.MiddleWare.Entities.RemainingBalance;

import java.io.IOException;
import java.math.BigDecimal;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.voltdb.VoltTable;
import org.voltdb.client.Client;
import org.voltdb.client.ClientResponse;
import org.voltdb.client.NoConnectionsException;
import org.voltdb.client.ProcCallException;


public class BalanceRepository {
    
    
    public long getUniqueBalanceId() throws SQLException, ClassNotFoundException{
            Connection conn = OracleDbHelper.getConnection();
 
            String callFunction = "{? = call package_balance.get_balance_id}";
            CallableStatement cstmt = conn.prepareCall(callFunction);
            
            cstmt.registerOutParameter(1, Types.INTEGER);
            cstmt.execute();

            long balanceId = cstmt.getLong(1);
            System.out.println(balanceId);

            cstmt.close();
            conn.close();
        return balanceId;
        
    }
    
    public long getUniquePartitionId() throws ClassNotFoundException, SQLException{
        long partitionId = 0;
        Connection conn = OracleDbHelper.getConnection();
        
        CallableStatement cstmt = conn.prepareCall("{? = call package_balance.get_partition_id}");
        cstmt.registerOutParameter(1, java.sql.Types.NUMERIC);
        cstmt.execute();
        
        partitionId = cstmt.getLong(1);
   
        
        conn.close();
        return partitionId;
        
    }
    
    public ResponseEntity OracleCreateBalances(long balanceId, long customerId, long partitionId, long packageId) throws ClassNotFoundException, SQLException {
   
        Connection conn = OracleDbHelper.getConnection();

        String sql = "{call package_balance.create_balance(?, ?, ?, ?, ?)}";
        CallableStatement cstmt = conn.prepareCall(sql);
        cstmt.setLong(1, balanceId);
        cstmt.setLong(2, customerId);
        cstmt.setLong(3, partitionId);
        cstmt.setLong(4, packageId);
        cstmt.setDouble(5,0);

        cstmt.execute();
        
        //aşım için oluşturulan balance
        sql = "{call package_balance.create_balance(?, ?, ?, ?, ?)}";
        cstmt = conn.prepareCall(sql);
        cstmt.setLong(1, balanceId + 1 );
        cstmt.setLong(2, customerId);
        cstmt.setLong(3, partitionId);
        cstmt.setLong(4,0);
        cstmt.setDouble(5,100);

        cstmt.execute();

        conn.close();

        return new ResponseEntity<>("balances created for oracle", HttpStatus.CREATED);
    
    }
  
    public long VoltdbCreateBalances(long balanceId, long customerId, long partitionId, long packageId) throws IOException, NoConnectionsException, ProcCallException, SQLException, ClassNotFoundException{
        
       
            VoltDbHelper voltDbHelper = new VoltDbHelper();
            Client client = voltDbHelper.client();

            client.callProcedure("RegisterBalance",customerId,balanceId ,packageId,partitionId,0);
            //for over-tariff
            long secondBalanceId = getUniqueBalanceId();
            client.callProcedure("RegisterBalance", customerId,secondBalanceId, 0 , partitionId,    100);
            
            //oracle tarafına 2. balanceId değerini gönderir
            return secondBalanceId;    
    }
    
    public List<RemainingBalance> getBalanceByMSISDN(String MSISDN) throws IOException, NoConnectionsException, ProcCallException, ClassNotFoundException, SQLException {
    List<RemainingBalance> remainingBalanceForUsers = new ArrayList<>();
    VoltDbHelper voltDbHelper = new VoltDbHelper();
    Client client = voltDbHelper.client();
    ClientResponse response;
    RemainingBalance balanceForUser = new RemainingBalance();

    response = client.callProcedure("getMSISDNWithId", MSISDN);
    VoltTable CustomerIdResult = response.getResults()[0];
    CustomerIdResult.advanceRow();
    long CUST_ID = CustomerIdResult.getLong(0);

    // Internet 
    response = client.callProcedure("ShowPackageAmountData", CUST_ID);
    VoltTable tableDataAmount = response.getResults()[0];
    BigDecimal data = null ;
    if (tableDataAmount.advanceRow() && tableDataAmount.advanceRow()) {//2. satıra geç eğer varsa
       data = tableDataAmount.getDecimalAsBigDecimal(0);  
        System.out.println(data);
    } else {// İkinci satır yoksa veya null değer varsa 
    
        System.out.println("datada 2. satir yok");
    }
   

    // Minutes
    response = client.callProcedure("ShowPackageAmountMinutes", CUST_ID);
    VoltTable tableMinutesAmount = response.getResults()[0];
    BigDecimal minute = null;
    if(tableMinutesAmount.advanceRow() && tableMinutesAmount.advanceRow()){//2. satıra geç
        
        minute = tableMinutesAmount.getDecimalAsBigDecimal(0);
        System.out.println(minute);
    }else{
        System.out.println("minutede 2. satır yok");
    }
   

    // SMS
    response = client.callProcedure("ShowPackageAmountSMS", CUST_ID);
    VoltTable tableSmsAmount = response.getResults()[0];
    BigDecimal SMS = null;
    if(tableSmsAmount.advanceRow() && tableSmsAmount.advanceRow()){//2. satıra geç
       
        SMS = tableSmsAmount.getDecimalAsBigDecimal(0);
        
        
    }else{
        System.out.println("SMSde 2. satır yok");
    }
    

    // Money balance
    BigDecimal amountMoney = null;
    
        response = client.callProcedure("ShowBalanceMoney", CUST_ID);
        VoltTable tableMoneyAmount = response.getResults()[0];
        if(tableMoneyAmount.advanceRow()){
            amountMoney = tableMoneyAmount.getDecimalAsBigDecimal(0);
        }else{
            System.out.println("Moneyde 2. satır yok");
        }
   
    
    //get package name
    PackageRepository packageRepository = new PackageRepository();
    String packageName =  packageRepository.getUserPackage(MSISDN);
    
    //get edate 
    String endDate = getEndDate(CUST_ID);
    
    //get name
    String FullName = getUserName(CUST_ID);
    
    //get package Amount balances 0->minute  1->SMS  2->data
    String[] packageAmounts = packageRepository.getPackageAmounts(MSISDN);
    
    
    balanceForUser.setPackageMinute(Long.parseLong(packageAmounts[0]));
    balanceForUser.setPackageSms(Long.parseLong(packageAmounts[1]));
    balanceForUser.setPackageData(Long.parseLong(packageAmounts[2]));
    balanceForUser.setUsername(FullName);
    balanceForUser.setEDate(endDate);
    balanceForUser.setPackageName(packageName);
    balanceForUser.setMSISDN(MSISDN);
    balanceForUser.setData(data);
    balanceForUser.setMinute(minute);
    balanceForUser.setAmountMoney(amountMoney.longValue());
    balanceForUser.setSms(SMS);
    
    remainingBalanceForUsers.add(balanceForUser);
    return remainingBalanceForUsers;
}
    
    public RemainingBalance getBalanceByMSISDNandroid(String MSISDN) throws IOException, NoConnectionsException, ProcCallException, ClassNotFoundException, SQLException {
    
    VoltDbHelper voltDbHelper = new VoltDbHelper();
    Client client = voltDbHelper.client();
    ClientResponse response;
    RemainingBalance balanceForUser = new RemainingBalance();

    response = client.callProcedure("getMSISDNWithId", MSISDN);
    VoltTable CustomerIdResult = response.getResults()[0];
    CustomerIdResult.advanceRow();
    long CUST_ID = CustomerIdResult.getLong(0);

    // Internet 
    response = client.callProcedure("ShowPackageAmountData", CUST_ID);
    VoltTable tableDataAmount = response.getResults()[0];
    BigDecimal data = null ;
    if (tableDataAmount.advanceRow() && tableDataAmount.advanceRow()) {//2. satıra geç eğer varsa
       data = tableDataAmount.getDecimalAsBigDecimal(0);  
        System.out.println(data);
    } else {// İkinci satır yoksa veya null değer varsa 
    
        System.out.println("datada 2. satir yok");
    }
   

    // Minutes
    response = client.callProcedure("ShowPackageAmountMinutes", CUST_ID);
    VoltTable tableMinutesAmount = response.getResults()[0];
    BigDecimal minute = null;
    if(tableMinutesAmount.advanceRow() && tableMinutesAmount.advanceRow()){//2. satıra geç
        
        minute = tableMinutesAmount.getDecimalAsBigDecimal(0);
        System.out.println(minute);
    }else{
        System.out.println("minutede 2. satır yok");
    }
   

    // SMS
    response = client.callProcedure("ShowPackageAmountSMS", CUST_ID);
    VoltTable tableSmsAmount = response.getResults()[0];
    BigDecimal SMS = null;
    if(tableSmsAmount.advanceRow() && tableSmsAmount.advanceRow()){//2. satıra geç
       
        SMS = tableSmsAmount.getDecimalAsBigDecimal(0);
        
        
    }else{
        System.out.println("SMSde 2. satır yok");
    }
    

    // Money balance
    long amountMoney = 0;
    if (SMS.equals(BigDecimal.ZERO) || data.equals(BigDecimal.ZERO) || minute.equals(BigDecimal.ZERO)) {
        response = client.callProcedure("ShowBalanceMoney", CUST_ID);
        VoltTable tableMoneyAmount = response.getResults()[0];
        if(tableMoneyAmount.advanceRow()){
            amountMoney = tableMoneyAmount.getLong(0);
        }else{
            System.out.println("Moneyde 2. satır yok");
        }
    }
    
    //get package name
    PackageRepository packageRepository = new PackageRepository();
    String packageName =  packageRepository.getUserPackage(MSISDN);
    
    //get edate 
    String endDate = getEndDate(CUST_ID);
    
    //get name
    String FullName = getUserName(CUST_ID);
    
    //get package Amount balances 0->minute  1->SMS  2->data
    String[] packageAmounts = packageRepository.getPackageAmounts(MSISDN);
    
    
    balanceForUser.setPackageMinute(Long.parseLong(packageAmounts[0]));
    balanceForUser.setPackageSms(Long.parseLong(packageAmounts[1]));
    balanceForUser.setPackageData(Long.parseLong(packageAmounts[2]));
    balanceForUser.setUsername(FullName);
    balanceForUser.setEDate(endDate);
    balanceForUser.setPackageName(packageName);
    balanceForUser.setMSISDN(MSISDN);
    balanceForUser.setData(data);
    balanceForUser.setMinute(minute);
    balanceForUser.setAmountMoney(amountMoney);
    balanceForUser.setSms(SMS);
    
    
    return balanceForUser;
}
    
    public String getEndDate(long CustId) throws ClassNotFoundException, SQLException{
        Connection conn = OracleDbHelper.getConnection();
       String sql = "SELECT EDate FROM BALANCE WHERE CUST_ID = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1, CustId); // CustId değişkeni yerine gerçek değeri koyun

        ResultSet resultSet = pstmt.executeQuery();
        String endDate = null;
        while (resultSet.next()) {
            endDate = resultSet.getString("EDate");
        }

        resultSet.close();
        pstmt.close();
        conn.close();
        
        
        return endDate;
    }
    
    public String getUserName(long CustId) throws ClassNotFoundException, SQLException{
       Connection conn = OracleDbHelper.getConnection();
        PreparedStatement pstmt = conn.prepareStatement("SELECT NAME, SURNAME FROM CUSTOMER WHERE CUST_ID = ?");

        pstmt.setLong(1, CustId); // custId değişkenini gerçek değerle değiştirin
        ResultSet resultSet = pstmt.executeQuery();
    
        String name = null;
        String surname = null;
        while (resultSet.next()) {
         name = resultSet.getString("NAME");
         surname = resultSet.getString("SURNAME");
        }
        conn.close();
        return name+" "+surname;
        
        
    }


    
    public ResponseEntity updateData(long CUST_ID, String MSISDN, long AMOUNT) throws ClassNotFoundException, SQLException {
        Connection conn = OracleDbHelper.getConnection();
        CallableStatement cstmt = conn.prepareCall("{call package_dmloperations.update_data(?, ?, ?)}");

        cstmt.setLong(1, CUST_ID);
        cstmt.setString(2, MSISDN);
        cstmt.setLong(3, AMOUNT);
      

        cstmt.executeUpdate();
        conn.close();
        
        
        return new ResponseEntity<>("User Data updated successfully.", HttpStatus.OK);
  
    }
    
    public ResponseEntity updateMinute(long CUST_ID, String MSISDN, long AMOUNT) throws ClassNotFoundException, SQLException {
        Connection conn = OracleDbHelper.getConnection();
        CallableStatement cstmt = conn.prepareCall("{call package_dmloperations.update_minutes(?, ?, ?)}");

        cstmt.setLong(1, CUST_ID);
        cstmt.setString(2, MSISDN);
        cstmt.setLong(3, AMOUNT);
      

        cstmt.executeUpdate();
        conn.close();

        return new ResponseEntity<>("User Minutes updated successfully.", HttpStatus.OK);
    }
    
    public ResponseEntity updateSMS(long CUST_ID, String MSISDN, long AMOUNT) throws ClassNotFoundException, SQLException {
        Connection conn = OracleDbHelper.getConnection();
        CallableStatement cstmt = conn.prepareCall("{call package_dmloperations.update_sms(?, ?, ?)}");

        cstmt.setLong(1, CUST_ID);
        cstmt.setString(2, MSISDN);
        cstmt.setLong(3, AMOUNT);
      

        cstmt.executeUpdate();
        conn.close();

        return new ResponseEntity<>("user SMS updated successfully.", HttpStatus.OK);
        
        
    }
    
    public ResponseEntity updateLvlMoney(long CUST_ID, String MSISDN, long AMOUNT) throws ClassNotFoundException, SQLException{
        
         Connection conn = OracleDbHelper.getConnection();
        CallableStatement cstmt = conn.prepareCall("{call package_dmloperations.update_lvl_money(?,?,?,?)}");

        cstmt.setLong(1, CUST_ID);
        cstmt.setString(2, MSISDN);
        cstmt.setLong(3,0);
        cstmt.setLong(4, AMOUNT);

        cstmt.executeUpdate();
        conn.close();
        
        return new ResponseEntity<>("user Money updated successfully.", HttpStatus.OK);
        
    }

    
    
}
