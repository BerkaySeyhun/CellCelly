
package com.CellCelly.MiddleWare.Entities;

import java.math.BigDecimal;
import java.util.Date;


public class RemainingBalance {
    private String MSISDN;
    private String PackageName;
    private String EDate;
    private BigDecimal data;
    private BigDecimal sms;
    private BigDecimal minute;
    private String username;
    private long packageData;
    private long packageMinute;
    private long packageSms;
    //aşım için
    private long AmountMoney;

    private long price;

    public RemainingBalance() {
    }

    
    public RemainingBalance(BigDecimal data, BigDecimal sms, BigDecimal minute, long AmountMoney, long price) {
        this.data = data;
        this.sms = sms;
        this.minute = minute;
        this.AmountMoney = AmountMoney;
        this.price = price;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    

    public long getPackageData() {
        return packageData;
    }

    public void setPackageData(long packageData) {
        this.packageData = packageData;
    }

    public long getPackageMinute() {
        return packageMinute;
    }

    public void setPackageMinute(long packageMinute) {
        this.packageMinute = packageMinute;
    }

    public long getPackageSms() {
        return packageSms;
    }

    public void setPackageSms(long packageSms) {
        this.packageSms = packageSms;
    }

    
    
    public String getMSISDN() {
        return MSISDN;
    }

    public void setMSISDN(String MSISDN) {
        this.MSISDN = MSISDN;
    }

    public String getPackageName() {
        return PackageName;
    }

    public void setPackageName(String PackageName) {
        this.PackageName = PackageName;
    }

    public String getEDate() {
        return EDate;
    }

    public void setEDate(String EDate) {
        this.EDate = EDate;
        
    }
    
    

    public BigDecimal getData() {
        return data;
    }

    public void setData(BigDecimal data) {
        this.data = data;
    }

    public BigDecimal getSms() {
        return sms;
    }

    public void setSms(BigDecimal sms) {
        this.sms = sms;
    }

    public BigDecimal getMinute() {
        return minute;
    }

    public void setMinute(BigDecimal minute) {
        this.minute = minute;
    }

    public long getAmountMoney() {
        return AmountMoney;
    }

    public void setAmountMoney(long AmountMoney) {
        this.AmountMoney = AmountMoney;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

   

    
    
    
   
}
