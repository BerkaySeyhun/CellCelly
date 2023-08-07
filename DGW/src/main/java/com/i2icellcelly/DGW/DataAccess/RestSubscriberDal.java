package com.i2icellcelly.DGW.DataAccess;

import org.springframework.stereotype.Repository;

@Repository
public class RestSubscriberDal implements ISubscriberDal{
    @Override
    public int getPartitionIDFromMSISDN() {
        return 0;
    }
}
