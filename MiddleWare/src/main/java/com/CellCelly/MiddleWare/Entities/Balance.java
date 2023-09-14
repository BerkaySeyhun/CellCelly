
package com.CellCelly.MiddleWare.Entities;

import java.util.Date;

public class Balance {
    private long balanceId;
    private long packageId;
    private long custId;
    private long partitionId;
    private int balLvlMinutes;
    private int balLvlSms;
    private int balLvlData;
    private Date sdate;
    private Date edate;
    private long price;

    public Balance(long balanceId, long packageId, long custId, long partitionId, int balLvlMinutes, int balLvlSms, int balLvlData, Date sdate, Date edate, long price) {
        this.balanceId = balanceId;
        this.packageId = packageId;
        this.custId = custId;
        this.partitionId = partitionId;
        this.balLvlMinutes = balLvlMinutes;
        this.balLvlSms = balLvlSms;
        this.balLvlData = balLvlData;
        this.sdate = sdate;
        this.edate = edate;
        this.price = price;
    }

    

    public Balance() {
    }

    public long getBalanceId() {
        return balanceId;
    }

    public void setBalanceId(long balanceId) {
        this.balanceId = balanceId;
    }

    public long getPackageId() {
        return packageId;
    }

    public void setPackageId(long packageId) {
        this.packageId = packageId;
    }

    public long getCustId() {
        return custId;
    }

    public void setCustId(long custId) {
        this.custId = custId;
    }

    public long getPartitionId() {
        return partitionId;
    }

    public void setPartitionId(long partitionId) {
        this.partitionId = partitionId;
    }

    public int getBalLvlMinutes() {
        return balLvlMinutes;
    }

    public void setBalLvlMinutes(int balLvlMinutes) {
        this.balLvlMinutes = balLvlMinutes;
    }

    public int getBalLvlSms() {
        return balLvlSms;
    }

    public void setBalLvlSms(int balLvlSms) {
        this.balLvlSms = balLvlSms;
    }

    public int getBalLvlData() {
        return balLvlData;
    }

    public void setBalLvlData(int balLvlData) {
        this.balLvlData = balLvlData;
    }

    public Date getSdate() {
        return sdate;
    }

    public void setSdate(Date sdate) {
        this.sdate = sdate;
    }

    public Date getEdate() {
        return edate;
    }

    public void setEdate(Date edate) {
        this.edate = edate;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Balance{" + "balanceId=" + balanceId + ", packageId=" + packageId + ", custId=" + custId + ", partitionId=" + partitionId + ", balLvlMinutes=" + balLvlMinutes + ", balLvlSms=" + balLvlSms + ", balLvlData=" + balLvlData + ", sdate=" + sdate + ", edate=" + edate + ", price=" + price + '}';
    }

 
    
   

    

  
}
