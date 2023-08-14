
package com.CellCelly.MiddleWare.Entities;


import java.util.Date;

public class Customer {
    private long custId;
    private String msisdn;
    private String name;
    private String surname;
    private String email;
    private String password;
    private Date sdate;
    private String status;
    private String securityKey;

    public Customer(long custId, String msisdn, String name, String surname, String email, String password, Date sdate, String status, String securityKey) {
        this.custId = custId;
        this.msisdn = msisdn;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
      
        this.sdate = sdate;
        this.status = status;
        this.securityKey = securityKey;
    }

    
  
    
    

    
    
    public Customer(){
        
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
        return "CustomerEntity{" + "custId=" + custId +
                ", msisdn=" + msisdn + ", name=" + name + ", surname=" + surname + 
                ", email=" + email + ", password=" + password + ", sdate=" + sdate + 
                ", securityKey=" + securityKey + ", status=" + status + '}';
    }
    
    
    
 
    
   
}
