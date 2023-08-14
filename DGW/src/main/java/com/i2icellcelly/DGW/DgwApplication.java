package com.i2icellcelly.DGW;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
Example JSON message formats to put in POST requests:

	SMS:
		{
		  "type": "sms",
		  "attributes": {
						  "location": Integer,
						  "msisdn": String,
						  "bMsisdn": String,
						  "date": String
		                }
		}
	Voice:
	    {
	      "type": "voice",
	      "attributes": {
	                      "location": Integer
	                      "msisdn": String
	                      "duration" Integer
	                      "bMsisdn": String
	                      "date": String
	                    }
	    }
	Data:
		{
	      "type": "date",
	      "attributes": {
	                      "location": Integer
	                      "msisdn": String
	                      "dataUsage" Integer
	                      "rGroup": Integer
	                      "date": String
	                    }
	    }

 */

@SpringBootApplication
public class DgwApplication {

	public static void main(String[] args) {
		SpringApplication.run(DgwApplication.class, args);
	}

}
