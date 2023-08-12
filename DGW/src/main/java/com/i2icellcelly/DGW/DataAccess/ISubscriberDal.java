package com.i2icellcelly.DGW.DataAccess;

/**
 * The DataAccessLayer to get the Partition IDs from Hazelcast.
 */
public interface ISubscriberDal {
    /**
     * The function to get the Partition IDs from the Hazelcast server. Needs the Hazelcast server
     * address and the map name to function.
     * @param msisdn    The MSISDN of the customer that the Partition ID will be found for.
     *                  The Partition ID is needed for faster lookups in the VoltDB.
     * @return          Returns the Partition ID of the customer. Throws a NumberFormatException if the
     *                  MSISDN is not found in the Hazelcast map.
     * @throws          NumberFormatException when the MSISDN in not found in the HZ server.
     */
    String getPartitionIDFromMSISDN(String msisdn) throws NumberFormatException;
}
