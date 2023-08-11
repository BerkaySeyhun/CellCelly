package org.example.cell;

import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;

public class UpdateBalanceMinutes extends VoltProcedure {
    public final SQLStmt MinutUpdate=new SQLStmt("UPDATE BALANCE \n"+
            "SET BAL_LVL_MINUTES=BAL_LVL_MINUTES+? \n"+
            "WHERE CUST_ID=?;");


    public VoltTable[] run(int CUST_ID , int BAL_LVL_MINUTES) throws VoltAbortException {
        voltQueueSQL(MinutUpdate, BAL_LVL_MINUTES ,CUST_ID);
        return voltExecuteSQL();
    }
}
