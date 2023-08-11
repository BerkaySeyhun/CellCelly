package org.example.cell;

import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;

public class ShowBalanceMoney extends VoltProcedure{
    public final SQLStmt showMoney = new SQLStmt(
            "SELECT BAL_LVL_MONEY FROM BALANCE WHERE CUST_ID = ? and PACKAGE_ID=0;");
    public VoltTable[] run(int CUST_ID)
            throws VoltProcedure.VoltAbortException {
        voltQueueSQL(showMoney, CUST_ID);
        return voltExecuteSQL();
    }
}
