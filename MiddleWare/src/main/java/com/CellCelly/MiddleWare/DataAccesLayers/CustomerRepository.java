
package com.CellCelly.MiddleWare.DataAccesLayers;

import com.CellCelly.MiddleWare.Crypt.Encryption;
import com.CellCelly.MiddleWare.DbConnectHelpers.OracleDbHelper;
import com.CellCelly.MiddleWare.DbConnectHelpers.VoltDbHelper;
import com.CellCelly.MiddleWare.Entities.CreateCustomerEntity;
import com.CellCelly.MiddleWare.Entities.Customer;
import com.CellCelly.MiddleWare.Entities.Login;
import java.io.IOException;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.voltdb.VoltTable;
import org.voltdb.client.Client;
import org.voltdb.client.ClientResponse;
import org.voltdb.client.NoConnectionsException;
import org.voltdb.client.ProcCallException;

public class CustomerRepository {
        private Long tempCustomerUID ;
        private Long tempBalanceUID;
        private String tempEncryptedPassword;
        private long tempPartitionId;
        
        //for hazelcast config
        public long getPartitionId(){
            return tempPartitionId;
        }
        
        private Encryption encryption = new Encryption();
        private BalanceRepository balanceRepository = new BalanceRepository();
        
        public List<Customer> getAll() throws ClassNotFoundException, SQLException{
            List<Customer> customers = new ArrayList<>();
            Connection conn = OracleDbHelper.getConnection();
            String sql = "SELECT CUST_ID,MSISDN,NAME,SURNAME,EMAIL,SDATE,STATUS,SECURITY_KEY FROM CUSTOMER";
            Statement stmt = conn.createStatement();
            stmt.executeQuery(sql);
            
            ResultSet rs = stmt.executeQuery(sql);
            
             while (rs.next()) {
                int customerId = rs.getInt("CUST_ID");
                String msisdn = rs.getString("MSISDN");
                String name = rs.getString("NAME");
                String surname = rs.getString("SURNAME");
                String email = rs.getString("EMAIL");
                Date sdate = rs.getDate("SDATE");
                String status = rs.getString("STATUS");
                String securityKey = rs.getString("SECURITY_KEY");

                 Customer customer = new Customer();
                customer.setCustId(customerId);
                customer.setMsisdn(msisdn);
                customer.setName(name);
                customer.setSurname(surname);
                customer.setEmail(email);
                customer.setSdate(sdate);
                customer.setStatus(status);
                customer.setSecurityKey(securityKey);

                customers.add(customer);
            }
            
            conn.close();
            return customers;
                    
            
        }
        
        public List<Customer> getCustomer(String MSISDN) throws IOException, ProcCallException {
            VoltDbHelper voltDbHelper = new VoltDbHelper();
            Client client = voltDbHelper.client();
            ClientResponse response;
            List<Customer> subscriber = new ArrayList<>();

            response = client.callProcedure("ShowUserInformation",MSISDN);
            VoltTable subscriberTable = response.getResults()[0];
            long SUBSC_ID = subscriberTable.fetchRow(0).getLong(0);
            String _MSISDN = subscriberTable.fetchRow(0).getString(1);
            String NAME = subscriberTable.fetchRow(0).getString(2);
            String SURNAME = subscriberTable.fetchRow(0).getString(3);
            String EMAIL = subscriberTable.fetchRow(0).getString(4);
            String PASSWORD = subscriberTable.fetchRow(0).getString(5);
            Date DATE = new Date(2023,8,5);
            String STATUS = subscriberTable.fetchRow(0).getString(7);
            String SECURITY_QUESTION = subscriberTable.fetchRow(0).getString(8);

        subscriber.add(new Customer(SUBSC_ID,_MSISDN,NAME,SURNAME,EMAIL,PASSWORD,DATE,STATUS,SECURITY_QUESTION));

        return  subscriber;

        }
        
   
        
      
    
     public ResponseEntity<String> login(Login login) throws ClassNotFoundException, SQLException {
        Connection conn = OracleDbHelper.getConnection();
        
        String sql = "{? = call package_customer.login(?, ?)}";
        String encryptedPassword = encryption.encrypt(login.getPassword());
        
        CallableStatement callableStatement = conn.prepareCall(sql);
        callableStatement.registerOutParameter(1, Types.INTEGER);
        callableStatement.setString(2, login.getMSISDN());
        callableStatement.setString(3,encryptedPassword);
        

        callableStatement.execute();
        int loginResult = callableStatement.getInt(1);
         System.out.println("login : "+loginResult);
         conn.close();
        if (loginResult == 1) {
            System.out.println("Successful login.");
            return ResponseEntity.ok("Successful login.");
        } else {
            System.out.println("Login failed.");
            return ResponseEntity.badRequest().body("Login failed.");
        }
}
    public ResponseEntity loginCheckAndroid(Login login) throws SQLException, ClassNotFoundException {
        OracleDbHelper oracleDbHelper = new OracleDbHelper();
        Connection connection = oracleDbHelper.getConnection();
        String encryptedPassword = encryption.encrypt(login.getPassword());

        CallableStatement callableStatement = connection.prepareCall("{? = call package_customer.login(?,?)}");
        callableStatement.registerOutParameter(1, Types.INTEGER);
        callableStatement.setString(2, login.getMSISDN());
        callableStatement.setString(3, encryptedPassword);
        callableStatement.execute();

        int checkUser = callableStatement.getInt(1);
        if (checkUser == 1) {
            return new ResponseEntity<>(login, HttpStatus.OK);

        } else
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
     
      
      
     public ResponseEntity OracleCreateCustomer(CreateCustomerEntity customer) throws ClassNotFoundException, SQLException {
        Connection conn = OracleDbHelper.getConnection();
      
        String sql = "{call package_customer.create_customer(?,?,?,?,?,?,?)}";
        CallableStatement cstmt = conn.prepareCall(sql);
        cstmt.setLong(1, tempCustomerUID);
        cstmt.setString(2, customer.getMsisdn());
        cstmt.setString(3, customer.getName());
        cstmt.setString(4, customer.getSurname());
        cstmt.setString(5, customer.getEmail());
        cstmt.setString(6, tempEncryptedPassword);
        cstmt.setString(7, customer.getSecurityKey());
        cstmt.execute();
        
    
        //kullanıcı için 2 balance oluşturulur
        balanceRepository.OracleCreateBalances(tempBalanceUID,tempCustomerUID,tempPartitionId,customer.getPackageId());
      
        conn.close();

        return new ResponseEntity<>("Customer registration is successful.", HttpStatus.CREATED);
        
       
    }
    
    public ResponseEntity VoltdbCreateCustomer(CreateCustomerEntity customer) throws ClassNotFoundException, SQLException, IOException, NoConnectionsException, ProcCallException {

        VoltDbHelper voltDbHelper = new VoltDbHelper();
        Client client = voltDbHelper.client();

        //bu iki yorum satırı arası ilk olarak voltdbye yapılsa daha iyi olur
        tempCustomerUID = getUniqueCustomerId();
    
        tempBalanceUID = balanceRepository.getUniqueBalanceId();
      
        tempEncryptedPassword = encryption.encrypt(customer.getPassword());
        
        tempPartitionId = balanceRepository.getUniquePartitionId();
        //---------------------------------------------------------------------
     

        client.callProcedure(
            "Register",
            tempCustomerUID,
            customer.getMsisdn(),
            customer.getName(),
            customer.getSurname(),
            customer.getEmail(),
            tempEncryptedPassword,
            customer.getSecurityKey()
        );
        
        //bu değişken içinde burdan dönen uniqueBalanceId değerini yazarız
        balanceRepository.VoltdbCreateBalances(tempBalanceUID,tempCustomerUID,tempPartitionId,customer.getPackageId());

        return new ResponseEntity<>("Customer registration is successful.", HttpStatus.CREATED);
      
}
    
    //unique id producer
     public long getUniqueCustomerId() throws SQLException, ClassNotFoundException {
        OracleDbHelper dbHelper = new OracleDbHelper();
        Connection connection = dbHelper.getConnection();
        String sql = "{ ? = call package_customer.get_customer_Id}";

        CallableStatement callableStatement = connection.prepareCall(sql);
        callableStatement.registerOutParameter(1, Types.INTEGER);
        callableStatement.execute();
        Long UI = callableStatement.getLong(1);

        connection.close();
        return UI;
    }
    
   
}
