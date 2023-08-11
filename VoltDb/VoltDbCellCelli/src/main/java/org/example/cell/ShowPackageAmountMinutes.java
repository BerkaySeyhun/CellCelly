package org.example.cell;

import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;

public class ShowPackageAmountMinutes  extends VoltProcedure {
    public final SQLStmt showVoiceAmount = new SQLStmt(
            "SELECT (SELECT AMOUNT_MINUTES FROM PACKAGE WHERE PACKAGE_ID =BALANCE.PACKAGE_ID) - BAL_LVL_MINUTES \n" +
                    " FROM BALANCE WHERE CUST_ID =?;");

    public VoltTable[] run(int  CUST_ID)
            throws VoltAbortException {
        voltQueueSQL(showVoiceAmount,CUST_ID);

        return voltExecuteSQL();
    }
}
