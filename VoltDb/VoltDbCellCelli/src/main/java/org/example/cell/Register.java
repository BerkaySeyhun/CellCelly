package org.example.cell;

import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;

import java.sql.Connection;
import java.sql.Timestamp;

public class Register extends VoltProcedure {

    // Constructor'da bağlantıyı alın

    public final SQLStmt insert=new SQLStmt(
            "INSERT INTO CUSTOMER (CUST_ID,MSISDN,NAME,SURNAME,EMAIL,PASSWORD,SECURITY_QUESTION) VALUES (?,?,?,?,?,?,?);");



    public VoltTable[] run(int CUST_ID, String MSISDN, String NAME, String SURNAME, String EMAIL, String PASSWORD,String SECURITY_QUESTION)  throws VoltAbortException {
        voltQueueSQL(insert,CUST_ID,MSISDN,NAME,SURNAME,EMAIL,PASSWORD,SECURITY_QUESTION);
        return voltExecuteSQL();
    }
}
