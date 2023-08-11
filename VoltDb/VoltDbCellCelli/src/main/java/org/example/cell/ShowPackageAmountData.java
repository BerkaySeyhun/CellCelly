package org.example.cell;

import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;

public class ShowPackageAmountData extends VoltProcedure {

    public final SQLStmt showDataAmount = new SQLStmt(
            "SELECT (SELECT AMOUNT_DATA  FROM PACKAGE WHERE PACKAGE_ID =BALANCE.PACKAGE_ID) - BAL_LVL_DATA  \n" +
                    " FROM BALANCE WHERE CUST_ID =?;");

    public VoltTable[] run(int  CUST_ID)
            throws VoltAbortException {
        voltQueueSQL(showDataAmount, CUST_ID);
        return voltExecuteSQL();
    }
}
