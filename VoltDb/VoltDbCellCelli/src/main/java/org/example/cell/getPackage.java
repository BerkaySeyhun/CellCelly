package org.example.cell;

import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;

public class getPackage extends VoltProcedure {

    public final SQLStmt getPackageQuery  = new SQLStmt("select package.*  from package, balance, customer where package.package_id = balance.package_id and balance.cust_id = customer.cust_id and customer.msisdn = ?;");

    public VoltTable[] run(String MSISDN)
            throws VoltAbortException {
        voltQueueSQL(getPackageQuery, MSISDN);
        return voltExecuteSQL();
    }

}
