package com.i2icellcelly.DGW.Entities;

import java.util.Date;

public class SubscriberMessage {
    private String _type;
    private Date _date;
    private Long _location;
    private Long _usageAmount;
    private String _senderMSISDN;
    private String _receiverMSISDN;
    private Long _ratingNumber;
    private int _partitionKey;

    public SubscriberMessage() {
        this._type = null;
        this._date = null;
        this._location = 0L;
        this._usageAmount = 0L;
        this._senderMSISDN = null;
        this._receiverMSISDN = null;
        this._ratingNumber = 0L;
        this._partitionKey = 0;
    }

    public SubscriberMessage(String _type, Date _date, Long _location, Long _usageAmount,
                             String _senderMSISDN, String _receiverMSISDN, Long _ratingNumber,
                             int _partitionKey) {
        this._type = _type;
        this._date = _date;
        this._location = _location;
        this._usageAmount = _usageAmount;
        this._senderMSISDN = _senderMSISDN;
        this._receiverMSISDN = _receiverMSISDN;
        this._ratingNumber = _ratingNumber;
        this._partitionKey = _partitionKey;
    }

    public String getType() {
        return _type;
    }

    public void setType(String _type) {
        this._type = _type;
    }

    public Date getDate() {
        return _date;
    }

    public void setDate(Date _date) {
        this._date = _date;
    }

    public Long getLocation() {
        return _location;
    }

    public void setLocation(Long _location) {
        this._location = _location;
    }

    public Long getUsageAmount() {
        return _usageAmount;
    }

    public void setUsageAmount(Long _usageAmount) {
        this._usageAmount = _usageAmount;
    }

    public String getSenderMSISDN() {
        return _senderMSISDN;
    }

    public void setSenderMSISDN(String _senderMSISDN) {
        this._senderMSISDN = _senderMSISDN;
    }

    public String getReceiverMSISDN() {
        return _receiverMSISDN;
    }

    public void setReceiverMSISDN(String _receiverMSISDN) {
        this._receiverMSISDN = _receiverMSISDN;
    }

    public Long getRatingNumber() {
        return _ratingNumber;
    }

    public void setRatingNumber(Long _ratingNumber) {
        this._ratingNumber = _ratingNumber;
    }

    public int get_partitionKey() {
        return _partitionKey;
    }

    public void set_partitionKey(int _partitionKey) {
        this._partitionKey = _partitionKey;
    }

    @Override
    public String toString() {
        return '{' +
                "\"Type\" : \"" + _type + "\", " +
                "\"Date\" : \"" + _date + "\", " +
                "\"Location\" : " + _location + ", " +
                "\"UsageAmount\" : " + _usageAmount + ", " +
                "\"SenderMSISDN\" : \"" + _senderMSISDN + "\", " +
                "\"ReceiverMSISDN\" : \"" + _receiverMSISDN + "\", " +
                "\"ratingNumber\" : " + _ratingNumber + ", " +
                "\"PartitionKey\" : " + _partitionKey +
                '}';
    }
}
