package org.example.cell;

import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;

public class UpdatePrice extends VoltProcedure {
    public final SQLStmt priceUpdate=new SQLStmt("UPDATE BALANCE\n"+
            "SET PRICE=PRICE+?\n"+
            "WHERE CUST_ID=?;"
    );
    public VoltTable[] run(int CUST_ID ,int PRICE ) throws VoltAbortException {
        voltQueueSQL(priceUpdate, PRICE,CUST_ID);
        return voltExecuteSQL();
    }
}
