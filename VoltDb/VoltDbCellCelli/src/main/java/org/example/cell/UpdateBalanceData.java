package org.example.cell;

import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;

public class UpdateBalanceData extends VoltProcedure {
    public final SQLStmt DataUpdate=new SQLStmt("UPDATE BALANCE \n"+
            "SET BAL_LVL_DATA=BAL_LVL_DATA+? \n"+
            "WHERE CUST_ID=?;");


    public VoltTable[] run(int CUST_ID ,int BAL_LVL_DATA) throws VoltAbortException {
        voltQueueSQL(DataUpdate, BAL_LVL_DATA ,CUST_ID);
        return voltExecuteSQL();
    }
}
