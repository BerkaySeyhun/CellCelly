
package com.CellCelly.MiddleWare.DataAccesLayers;

import com.CellCelly.MiddleWare.DbConnectHelpers.OracleDbHelper;
import com.CellCelly.MiddleWare.DbConnectHelpers.VoltDbHelper;
import com.CellCelly.MiddleWare.Entities.Package;
import com.CellCelly.MiddleWare.Entities.PackageIdName;

import java.io.IOException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

import oracle.jdbc.OracleTypes;

import org.voltdb.VoltTable;
import org.voltdb.client.Client;
import org.voltdb.client.ClientResponse;
import org.voltdb.client.NoConnectionsException;
import org.voltdb.client.ProcCallException;


public class PackageRepository {
    
    //bu idye sahip bir paket yok.  bu package idye sahip balance aşım balancesi olarak kullanılır
    public static long specialPackageId = 0;
    
    public List<Package> getAllPackages() throws SQLException, ClassNotFoundException {
        OracleDbHelper oracleDbHelper = new OracleDbHelper();
        Connection connection = oracleDbHelper.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT PACKAGE_ID,PACKAGE_NAME,PRICE,AMOUNT_MINUTES,AMOUNT_DATA,AMOUNT_SMS,PERIOD FROM PACKAGE");
        List<Package> packageList = new ArrayList<Package>();
        while (resultSet.next()) {
            long packageId = resultSet.getLong("PACKAGE_ID");
            String packageName = resultSet.getString("PACKAGE_NAME");
            double price = resultSet.getDouble("PRICE");
            long amountMinutes = resultSet.getLong("AMOUNT_MINUTES");
            long amountData = resultSet.getLong("AMOUNT_DATA");
            long amountSms = resultSet.getLong("AMOUNT_SMS");
            long period = resultSet.getLong("PERIOD");
        
            Package pkg = new Package(packageId, packageName, price, amountMinutes, amountData, amountSms, period);
            packageList.add(pkg);
      
       
        }
        connection.close();
        return packageList;
  }
    
    //paketlerin id ve name döndürür
    public List<PackageIdName> getAllPackagesIdName() throws SQLException, ClassNotFoundException {
        List<PackageIdName> packageList = new ArrayList<>();

        Connection conn = OracleDbHelper.getConnection();
        CallableStatement cstmt = conn.prepareCall("{call package_package.get_all_packages(?)}");
        cstmt.registerOutParameter(1,OracleTypes.CURSOR);
        cstmt.execute();

        ResultSet rs = (ResultSet) cstmt.getObject(1);
        while (rs.next()) {
            long packageId = rs.getLong("PACKAGE_ID");
            String packageName = rs.getString("PACKAGE_NAME");

            PackageIdName packageIdName = new PackageIdName(packageId, packageName);
            packageList.add(packageIdName);
        }

        conn.close();

        return packageList;
    }
   
   public List<Package> getPackageByMSISDN(String MSISDN) throws IOException, NoConnectionsException, ProcCallException{
        VoltDbHelper voltDbHelper = new VoltDbHelper();
        Client client = voltDbHelper.client();
        ClientResponse response;

        List<Package> packageInfo = new ArrayList<>();

        response = client.callProcedure("GetPackage", MSISDN);
        VoltTable tablePackageInfo = response.getResults()[0];

        while (tablePackageInfo.advanceRow()) {
            long packageID = tablePackageInfo.getLong("package_id");
            String packageName = tablePackageInfo.getString("package_name");
            double price = tablePackageInfo.getDouble("price");
            long amountMinutes = tablePackageInfo.getLong("amount_minutes");
            long amountData = tablePackageInfo.getLong("amount_data");
            long amountSms = tablePackageInfo.getLong("amount_sms");
            long period = tablePackageInfo.getLong("period");

            packageInfo.add(new Package(packageID, packageName, price, amountMinutes, amountData, amountSms, period));
        }
        
        return packageInfo;
       
   }
   
   
   
  public String getUserPackage(String msisdn) throws ClassNotFoundException, SQLException {
    String packageName = null;
    String sql = "SELECT package.package_name FROM CUSTOMER INNER JOIN BALANCE ON customer.cust_id = balance.cust_id \n" +
                 "INNER JOIN PACKAGE ON balance.package_id = package.package_id WHERE customer.msisdn = ? and balance.package_id != 0";

    Connection conn = OracleDbHelper.getConnection();
    PreparedStatement pstmt = conn.prepareStatement(sql);

    pstmt.setString(1, msisdn); // Parametreyi ayarla

    ResultSet resultSet = pstmt.executeQuery(); // Sorguyu çalıştır ve sonuç kümesini al

    if (resultSet.next()) { // İlk satırı kontrol et
        packageName = resultSet.getString("package_name"); // "package_name" sütunun değerini al
        System.out.println("PackageName = " + packageName);
    }
    
    conn.close();
    resultSet.close(); // Sonuç kümesini kapat
    pstmt.close(); // PreparedStatement'ı kapat

    return packageName;
}
  
  public Package getPackageByMSISDNinObject(String MSISDN) throws IOException, ProcCallException {
        VoltDbHelper voltDbHelper = new VoltDbHelper();
        Client client = voltDbHelper.client();
        ClientResponse response;


        response = client.callProcedure("getPackage",MSISDN);
        VoltTable tablePackageInfo = response.getResults()[0];
        tablePackageInfo.advanceRow();
        long packageID =tablePackageInfo.getLong(0);
        String packageName =tablePackageInfo.getString(1);
        long amountMinute =tablePackageInfo.getLong(2);
        long amountData =tablePackageInfo.getLong(3);
        long amountSMS =tablePackageInfo.getLong(4);
        long period =tablePackageInfo.getLong(5);


        return (new Package(packageID,packageName,0,amountMinute,amountData,amountSMS,period));

    }
  
   
       ////bunu kullanacam
   public String[] getPackageAmounts(String MSISDN) throws ClassNotFoundException, SQLException{
       String[] packageAmounts = new String[3];
        String packageName = getUserPackage(MSISDN);
        Connection conn = OracleDbHelper.getConnection();
        
        String sql = "SELECT AMOUNT_MINUTES, AMOUNT_SMS, AMOUNT_DATA FROM PACKAGE WHERE PACKAGE_NAME = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, packageName);
        
        ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                packageAmounts[0] = resultSet.getString("AMOUNT_MINUTES");
                packageAmounts[1] = resultSet.getString("AMOUNT_SMS");
                packageAmounts[2] = resultSet.getString("AMOUNT_DATA");
            }
       
            conn.close();
        return packageAmounts;
       
   }

       
   
     
 
}
