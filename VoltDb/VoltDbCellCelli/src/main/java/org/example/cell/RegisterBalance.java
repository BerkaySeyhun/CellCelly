package org.example.cell;

import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;

public class RegisterBalance extends VoltProcedure {
    public final SQLStmt balanceinsert=new SQLStmt("INSERT INTO BALANCE (BALANCE_ID,CUST_ID,PACKAGE_ID,PARTITION_ID,BAL_LVL_MONEY) VALUES (?,?,?,?,?);");


    public VoltTable[] run(int CUST_ID, int BALANCE_ID, int PACKAGE_ID,int PARTITION_ID,int BAL_LVL_MONEY)  throws VoltAbortException {
        voltQueueSQL(balanceinsert,BALANCE_ID,CUST_ID,PACKAGE_ID,PARTITION_ID,BAL_LVL_MONEY);
        return voltExecuteSQL();
    }
}

