package com.CellCelly.MiddleWare.Entities;

import java.util.Date;


public class CreateCustomerEntity {
    private long custId;
    private String msisdn;
    private String name;
    private String surname;
    private String email;
    private String password;
    private Long balanceId;
    private Date sdate;
    private String status;
    private String securityKey;
    private Long packageId;

    public CreateCustomerEntity(long custId, String msisdn, String name, String surname, String email, String password, Long balanceId, Date sdate, String status, String securityKey, Long packageId) {
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
    

    
    
    public CreateCustomerEntity(){
        
    }

    public Long getBalanceId() {
        return balanceId;
    }

    public void setBalanceId(Long balanceId) {
        this.balanceId = balanceId;
    }

    public Long getPackageId() {
        return packageId;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }

    
    public long getCustId() {
        return custId;
    }

    public void setCustId(long custId) {
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

 
    public Date getSdate() {
        return sdate;
    }

    public void setSdate(Date sdate) {
        this.sdate = sdate;
    }

    public String getSecurityKey() {
        return securityKey;
    }

    public void setSecurityKey(String securityKey) {
        this.securityKey = securityKey;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CreateCustomerEntity{" + "custId=" + custId + ", msisdn=" + msisdn + ", name=" + name + ", surname=" + surname + ", email=" + email + ", password=" + password + ", balanceId=" + balanceId + ", sdate=" + sdate + ", status=" + status + ", securityKey=" + securityKey + ", packageId=" + packageId + '}';
    }
}
