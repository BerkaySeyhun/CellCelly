package org.example.cell;

import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;

public class UpdatEBalanceSMS extends VoltProcedure {
    public final SQLStmt smsUpdate=new SQLStmt("UPDATE BALANCE \n"+
            "SET BAL_LVL_SMS=BAL_LVL_SMS+? \n"+
            "WHERE CUST_ID=?;"
            );

    public VoltTable[] run(int CUST_ID,int BAL_LVL_SMS) throws VoltAbortException{
        voltQueueSQL(smsUpdate, BAL_LVL_SMS,CUST_ID);
        return voltExecuteSQL();
    }
}
