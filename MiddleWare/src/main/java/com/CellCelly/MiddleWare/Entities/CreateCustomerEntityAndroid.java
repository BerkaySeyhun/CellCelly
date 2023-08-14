package com.CellCelly.MiddleWare.Entities;

import java.util.Date;


public class CreateCustomerEntityAndroid {
    private String custId;
    private String msisdn;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String balanceId;
    private String sdate;
    private String status;
    private String securityKey;
    private String packageId;

    public CreateCustomerEntityAndroid() {
    }

    public CreateCustomerEntityAndroid(String custId, String msisdn, String name, String surname, String email, String password, String balanceId, String sdate, String status, String securityKey, String packageId) {
        this.custId = custId;
        this.msisdn = msisdn;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.balanceId = balanceId;
        this.sdate = sdate;
        this.status = status;
        this.securityKey = securityKey;
        this.packageId = packageId;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBalanceId() {
        return balanceId;
    }

    public void setBalanceId(String balanceId) {
        this.balanceId = balanceId;
    }

    public String getSdate() {
        return sdate;
    }

    public void setSdate(String sdate) {
        this.sdate = sdate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSecurityKey() {
        return securityKey;
    }

    public void setSecurityKey(String securityKey) {
        this.securityKey = securityKey;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    
}
