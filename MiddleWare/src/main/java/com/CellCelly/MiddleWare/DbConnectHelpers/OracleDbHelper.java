
package com.CellCelly.MiddleWare.DbConnectHelpers;


import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.SQLException;



public class OracleDbHelper {
       public static Connection getConnection() throws ClassNotFoundException, SQLException{
            String driver = "oracle.jdbc.driver.OracleDriver";
            String url = "jdbc:oracle:thin:@34.134.248.97:1521:oracledb";
            String username = "celly";
            String password = "010823";
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, username, password);
           
          return conn;
       } 
}