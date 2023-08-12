package com.i2icellcelly.DGW.Entities;

import java.util.Date;

/**
 * The DGWMessage class forms the structure of the message that will be sent to the OCS
 * via Akka. The main purpose of this class is to provide an easy-to-use interface for creating
 * a String suitable for communicating with the OCS. The structure of the message encompasses
 * all possible types of communication, if there is no information corresponding to any of the
 * message's fields, default values are used.
 */
public class DGWMessage {
    private String _type;
    private Date _date;
    private Long _location;
    private Long _usageAmount;
    private String _senderMSISDN;
    private String _receiverMSISDN;
    private Long _ratingNumber;
    private int _partitionKey;

    /**
     * Constructor.
     */
    public DGWMessage() {
        this._type = null;
        this._date = null;
        this._location = 0L;
        this._usageAmount = 0L;
        this._senderMSISDN = null;
        this._receiverMSISDN = null;
        this._ratingNumber = 0L;
        this._partitionKey = 0;
    }

    /**
     * Constructor, overloaded with every field of the DGWMessage.
     * @param _type             The type of the communication. Can be SMS, Data or Voice
     * @param _date             The starting date of the communication. Format is given in the factory.
     * @param _location         The location of the sender party.
     * @param _usageAmount      The usage amount for Data.
     * @param _senderMSISDN     The MSISDN of the sender party.
     * @param _receiverMSISDN   The MSISDN of the receiver party.
     * @param _ratingNumber     The rating number used for Data.
     * @param _partitionKey     The Partition Key to be used for VoltDB queries.
     */
    public DGWMessage(String _type, Date _date, Long _location, Long _usageAmount,
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

    /**
     * The String is in the JSON format.
     * @return    The message that will be sent to the OCS in the JSON format.
     */
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
