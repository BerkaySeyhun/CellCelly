package org.example.cell;

import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;

public class getMSISDNWithId extends VoltProcedure {

    public final SQLStmt showDataAmount = new SQLStmt(
            "SELECT CUST_ID FROM  CUSTOMER WHERE MSISDN =?");

    public VoltTable[] run(String MSISDN)
            throws VoltAbortException {
        voltQueueSQL(showDataAmount, MSISDN);
        return voltExecuteSQL();
    }
}
