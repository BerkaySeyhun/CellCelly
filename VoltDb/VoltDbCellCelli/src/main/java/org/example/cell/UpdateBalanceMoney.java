package org.example.cell;

import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;

public class UpdateBalanceMoney extends VoltProcedure {
    public final SQLStmt priceUpdate=new SQLStmt("UPDATE BALANCE\n"+
            "SET BAL_LVL_MONEY=BAL_LVL_MONEY+?\n"+
            "WHERE CUST_ID=? and PACKAGE_ID=0"
    );
    public VoltTable[] run(int CUST_ID , int BAL_LVL_MONEY ) throws VoltAbortException {
        voltQueueSQL(priceUpdate, BAL_LVL_MONEY,CUST_ID);
        return voltExecuteSQL();
    }
}
