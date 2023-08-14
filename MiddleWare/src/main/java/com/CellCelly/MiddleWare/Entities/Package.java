
package com.CellCelly.MiddleWare.Entities;


public class Package{
    private long packageId;
    private String packageName;
    private double price;
    private long amountMinutes;
    private long amountData;
    private long amountSms;
    private long period;

  public Package(long packageId, String packageName, double price, long amountMinutes, long amountData, long amountSms, long period) {
    this.packageId = packageId;
    this.packageName = packageName;
    this.price = price;
    this.amountMinutes = amountMinutes;
    this.amountData = amountData;
    this.amountSms = amountSms;
    this.period = period;
}

    public Package() {
    }

  

    public long getPackageId() {
        return packageId;
    }

    public void setPackageId(long packageId) {
        this.packageId = packageId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getAmountMinutes() {
        return amountMinutes;
    }

    public void setAmountMinutes(long amountMinutes) {
        this.amountMinutes = amountMinutes;
    }

    public long getAmountData() {
        return amountData;
    }

    public void setAmountData(long amountData) {
        this.amountData = amountData;
    }

    public long getAmountSms() {
        return amountSms;
    }

    public void setAmountSms(long amountSms) {
        this.amountSms = amountSms;
    }

    public long getPeriod() {
        return period;
    }

    public void setPeriod(long period) {
        this.period = period;
    }

    

   
    @Override
    public String toString() {
        return "Package [packageId=" + packageId + ", packageName=" + packageName + ", price=" + price
                + ", amountMinutes=" + amountMinutes + ", amountData=" + amountData + ", amountSms=" + amountSms
                + ", period=" + period + "]";
    }
}
