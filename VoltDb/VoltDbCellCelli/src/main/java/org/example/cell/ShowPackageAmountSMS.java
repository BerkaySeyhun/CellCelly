package org.example.cell;

import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;

public class ShowPackageAmountSMS extends VoltProcedure {

    public final SQLStmt showSMSAmount = new SQLStmt(
            "SELECT (SELECT AMOUNT_SMS FROM PACKAGE WHERE PACKAGE_ID =BALANCE.PACKAGE_ID) - BAL_LVL_SMS \n" +
                    " FROM BALANCE WHERE CUST_ID = ?;");

    public VoltTable[] run(int  CUST_ID)
            throws VoltAbortException {
        voltQueueSQL(showSMSAmount, CUST_ID);
        return voltExecuteSQL();
    }

}
