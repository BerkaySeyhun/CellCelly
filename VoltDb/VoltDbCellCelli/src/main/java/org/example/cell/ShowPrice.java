package org.example.cell;

import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;

public class ShowPrice extends VoltProcedure {
    public final SQLStmt showPrice = new SQLStmt(
            "SELECT PRICE FROM BALANCE WHERE CUST_ID = ?;");
    public VoltTable[] run(int CUST_ID)
            throws VoltAbortException {
        voltQueueSQL(showPrice, CUST_ID);
        return voltExecuteSQL();
    }
}
