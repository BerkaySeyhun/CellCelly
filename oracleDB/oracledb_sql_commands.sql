/************************************************
*                                               *
*         CREATING USER AND GRANTING IT         *
*                                               *
************************************************/

CREATE USER celly IDENTIFIED BY 010823;
GRANT RESOURCE, CONNECT, UNLIMITED TABLESPACE TO celly;

/************************************************
*                                               *
*  CREATING TABLES, CONSTRAINTS AND SEQUENCES   *
*                                               *
************************************************/

CREATE TABLE CUSTOMER(
    CUST_ID NUMBER,
    MSISDN VARCHAR2(15) NOT NULL UNIQUE,
    NAME VARCHAR2(100) NOT NULL,
    SURNAME VARCHAR2(100) NOT NULL,
    EMAIL VARCHAR2(100) NOT NULL,
    PASSWORD VARCHAR2(20) NOT NULL,
    SDATE DATE DEFAULT SYSDATE,
    STATUS VARCHAR2(2) DEFAULT 'A',
    SECURITY_KEY VARCHAR2(200) NOT NULL
);

ALTER TABLE CUSTOMER ADD CONSTRAINT cust_id_pk PRIMARY KEY (CUST_ID);

CREATE SEQUENCE cust_id_sequence start with 1 increment by 1;

CREATE TABLE PACKAGE(
    PACKAGE_ID NUMBER, 
    PACKAGE_NAME VARCHAR2(200) UNIQUE,
    PRICE NUMBER,
    AMOUNT_MINUTES NUMBER,
    AMOUNT_DATA NUMBER,
    AMOUNT_SMS NUMBER,
    PERIOD NUMBER 
);

ALTER TABLE PACKAGE ADD CONSTRAINT package_id_pk PRIMARY KEY (PACKAGE_ID);

CREATE SEQUENCE package_id_sequence start with 1 increment by 1;

CREATE TABLE BALANCE(
    BALANCE_ID NUMBER,
    PACKAGE_ID NUMBER,
    CUST_ID NUMBER,
    PARTITION_ID NUMBER,
    BAL_LVL_MINUTES NUMBER DEFAULT 0,
    BAL_LVL_SMS NUMBER DEFAULT 0,
    BAL_LVL_DATA NUMBER DEFAULT 0,
    BAL_LVL_MONEY NUMBER DEFAULT 100,
    SDATE DATE DEFAULT SYSDATE,
    EDATE DATE DEFAULT SYSDATE,
    PRICE NUMBER
);

ALTER TABLE BALANCE ADD(
    CONSTRAINT balance_id_pk PRIMARY KEY (BALANCE_ID),
    CONSTRAINT cust_id_fk FOREIGN KEY (CUST_ID) REFERENCES CUSTOMER(CUST_ID) ON DELETE CASCADE,
    CONSTRAINT package_id_fk FOREIGN KEY (PACKAGE_ID) REFERENCES PACKAGE(PACKAGE_ID) ON DELETE CASCADE
);

CREATE SEQUENCE balance_id_sequence start with 1 increment by 1;

ALTER TABLE BALANCE ADD BAL_LVL_MONEY NUMBER;

CREATE SEQUENCE partition_id_sequence start with 1 increment by 1;

/************************************************
*                                               *
*         ADDING DATA TO EXISTING TABLES        *
*                                               *
************************************************/

INSERT INTO CUSTOMER(CUST_ID,MSISDN,NAME,SURNAME,EMAIL,PASSWORD,SDATE,STATUS,SECURITY_KEY)
VALUES (CUST_ID_SEQUENCE.nextval,'5351234567','Burak','Celiloðlu','burak@nomail.com','123',SYSDATE,'A','._.');
INSERT INTO CUSTOMER(CUST_ID,MSISDN,NAME,SURNAME,EMAIL,PASSWORD,SDATE,STATUS,SECURITY_KEY)
VALUES (CUST_ID_SEQUENCE.nextval,'5351234551','Ramo','Abc','ramoabc@nomail.com','456',SYSDATE,'A','mw');

INSERT INTO BALANCE (balance_id,cust_id,package_id,bal_lvl_minutes, bal_lvl_sms, bal_lvl_data,sdate,edate) 
               VALUES(balance_id_sequence.nextval,165, 4, default, default, default, SYSDATE, SYSDATE);
INSERT INTO BALANCE (balance_id,cust_id,package_id,bal_lvl_minutes, bal_lvl_sms, bal_lvl_data,sdate,edate) 
               VALUES(balance_id_sequence.nextval,166, 5, default, default, default, SYSDATE, SYSDATE);
               
INSERT INTO PACKAGE (PACKAGE_ID, PACKAGE_NAME, PRICE, AMOUNT_MINUTES, AMOUNT_DATA, AMOUNT_SMS) VALUES (0,'WALLET',0,0,0,0);
commit;
INSERT INTO PACKAGE (PACKAGE_ID, PACKAGE_NAME, PRICE, AMOUNT_MINUTES, AMOUNT_DATA, AMOUNT_SMS, PERIOD) VALUES (package_id_sequence.nextval ,'ANKARA',50,3000,3,3000,30);
commit;
INSERT INTO PACKAGE (PACKAGE_ID, PACKAGE_NAME, PRICE, AMOUNT_MINUTES, AMOUNT_DATA, AMOUNT_SMS, PERIOD) VALUES (package_id_sequence.nextval ,'BURSA',65,5000,5,5000,30);
commit;
INSERT INTO PACKAGE (PACKAGE_ID, PACKAGE_NAME, PRICE, AMOUNT_MINUTES, AMOUNT_DATA, AMOUNT_SMS, PERIOD) VALUES (package_id_sequence.nextval ,'DIYARBAKIR',80,7000,7,7000,30);
commit;
INSERT INTO PACKAGE (PACKAGE_ID, PACKAGE_NAME, PRICE, AMOUNT_MINUTES, AMOUNT_DATA, AMOUNT_SMS, PERIOD) VALUES (package_id_sequence.nextval ,'RIZE',95,10000,10,10000,30);
commit;
INSERT INTO PACKAGE (PACKAGE_ID, PACKAGE_NAME, PRICE, AMOUNT_MINUTES, AMOUNT_DATA, AMOUNT_SMS, PERIOD) VALUES (package_id_sequence.nextval ,'CANAKKALE',120,1700,17,700,17);
commit;
INSERT INTO PACKAGE (PACKAGE_ID, PACKAGE_NAME, PRICE, AMOUNT_MINUTES, AMOUNT_DATA, AMOUNT_SMS, PERIOD) VALUES (package_id_sequence.nextval ,'ESKISEHIR',140,1000,20,5000,30);
commit; 

UPDATE PACKAGE 
SET AMOUNT_DATA = AMOUNT_DATA * 1024;

/************************************************
*                                               *
*   CREATING PACKAGE FOR CUSTOMER PROCEDURES    *
*                                               *
************************************************/

create or replace PACKAGE package_customer IS
    FUNCTION login (U_MSISDN IN CUSTOMER.MSISDN%TYPE, U_PASSWORD IN CUSTOMER.PASSWORD%TYPE) RETURN NUMBER;
    FUNCTION get_customer_id RETURN NUMBER;
    FUNCTION get_user_package (p_msisdn customer.msisdn%type) RETURN package.package_name%type;
    FUNCTION get_remaining_minutes (p_msisdn customer.msisdn%type) RETURN NUMBER;
    FUNCTION get_remaining_data (p_msisdn customer.msisdn%type) RETURN NUMBER;
    FUNCTION get_remaining_sms (p_msisdn customer.msisdn%type) RETURN NUMBER;
    FUNCTION forget_password(P_EMAIL IN CUSTOMER.EMAIL%TYPE, P_SECURITY_KEY IN CUSTOMER.SECURITY_KEY%TYPE) RETURN NVARCHAR2;
    PROCEDURE create_customer(S_CUST_ID IN CUSTOMER.CUST_ID%TYPE,S_MSISDN IN CUSTOMER.MSISDN%TYPE, S_NAME IN CUSTOMER.NAME%TYPE, S_SURNAME IN CUSTOMER.SURNAME%TYPE, 
                                S_EMAIL IN CUSTOMER.EMAIL%TYPE, S_PASSWORD IN CUSTOMER.PASSWORD%TYPE,
                                P_SECURTY_KEY IN CUSTOMER.SECURITY_KEY%TYPE);

END package_customer;

create or replace PACKAGE BODY package_customer IS

    FUNCTION login (U_MSISDN IN CUSTOMER.MSISDN%TYPE, U_PASSWORD IN CUSTOMER.PASSWORD%TYPE) RETURN NUMBER
    AS
        match_count NUMBER;
    BEGIN
        SELECT COUNT(*) INTO match_count FROM CUSTOMER WHERE MSISDN = U_MSISDN AND password = U_PASSWORD; 
        COMMIT;
        IF match_count = 0 THEN
            RETURN 0;
        ELSIF match_count >= 1 THEN
            RETURN 1;
        END IF;
        EXCEPTION
        WHEN CASE_NOT_FOUND
        THEN RETURN 0;
    END;



    FUNCTION get_customer_id RETURN NUMBER 
    AS
        u_id NUMBER;
    BEGIN 
        u_id := CUST_ID_SEQUENCE.nextval;
        COMMIT;
    RETURN u_id ;
    END get_customer_id;



    FUNCTION get_user_package(p_msisdn customer.msisdn%type) RETURN package.package_name%type
    AS
        v_package_name package.package_name%type;
    BEGIN
        SELECT package.package_name INTO v_package_name FROM CUSTOMER INNER JOIN BALANCE ON customer.cust_id = balance.cust_id
                                                    INNER JOIN PACKAGE ON balance.package_id = package.package_id WHERE customer.msisdn = p_msisdn;
                                                    COMMIT;
        return v_package_name;
    END;



    FUNCTION get_remaining_minutes(p_msisdn customer.msisdn%type) RETURN NUMBER
    AS
        remaining_minutes number;
    BEGIN
        SELECT (package.amount_minutes - balance.bal_lvl_minutes) INTO remaining_minutes FROM CUSTOMER INNER JOIN BALANCE ON customer.cust_id = balance.cust_id
                                                    INNER JOIN PACKAGE ON balance.package_id = package.package_id WHERE customer.msisdn = p_msisdn;
                                                    COMMIT;
        return remaining_minutes;
    END;



    FUNCTION get_remaining_data(p_msisdn customer.msisdn%type) RETURN NUMBER
    AS
        remaining_data number;
    BEGIN
        SELECT (package.amount_data - balance.bal_lvl_data) INTO remaining_data FROM CUSTOMER INNER JOIN BALANCE ON customer.cust_id = balance.cust_id
                                                    INNER JOIN PACKAGE ON balance.package_id = package.package_id WHERE customer.msisdn = p_msisdn;
                                                    COMMIT;
        return remaining_data;
    END;



    FUNCTION get_remaining_sms(p_msisdn customer.msisdn%type) RETURN NUMBER
    AS
        remaining_sms number;
    BEGIN
        SELECT (package.amount_sms - balance.bal_lvl_sms) INTO remaining_sms FROM CUSTOMER INNER JOIN BALANCE ON customer.cust_id = balance.cust_id
                                                    INNER JOIN PACKAGE ON balance.package_id = package.package_id WHERE customer.msisdn = p_msisdn;
                                                    COMMIT;
        return remaining_sms;
    END;

    FUNCTION forget_password (P_EMAIL IN CUSTOMER.EMAIL%TYPE, P_SECURITY_KEY IN CUSTOMER.SECURITY_KEY%TYPE) RETURN NVARCHAR2
    AS
        P_PASSWORD customer.PASSWORD%TYPE;
    BEGIN
        SELECT customer.password into P_PASSWORD FROM customer WHERE  email = P_EMAIL AND security_key = P_SECURITY_KEY;
        COMMIT;
        IF P_PASSWORD IS NULL THEN
            RETURN 'Invalid phone number';
        ELSIF P_PASSWORD IS NOT NULL THEN
            RETURN P_PASSWORD;
        END IF;
    END; 

    PROCEDURE create_customer(S_CUST_ID IN CUSTOMER.CUST_ID%TYPE,S_MSISDN IN CUSTOMER.MSISDN%TYPE, S_NAME IN CUSTOMER.NAME%TYPE, S_SURNAME IN CUSTOMER.SURNAME%TYPE, 
                                S_EMAIL IN CUSTOMER.EMAIL%TYPE, S_PASSWORD IN CUSTOMER.PASSWORD%TYPE,
                                P_SECURTY_KEY IN CUSTOMER.SECURITY_KEY%TYPE) IS
        BEGIN
            INSERT INTO CUSTOMER (cust_id,msisdn,name,surname,email,password,sdate,status,security_key) 
               VALUES(S_CUST_ID,s_msisdn,s_name,s_surname,s_email,s_password,SYSDATE,default,P_SECURTY_KEY);
            COMMIT;
        END;
END package_customer;

/************************************************
*                                               *
* CREATING PACKAGE FOR DMLOPERATIONS PROCEDURES *
*                                               *
************************************************/

create or replace PACKAGE PACKAGE_DMLOPERATIONS IS
    PROCEDURE update_minutes(p_cust_id in customer.cust_id%type, p_msisdn in customer.msisdn%type, amount in number);
    PROCEDURE update_data(p_cust_id in customer.cust_id%type, p_msisdn in customer.msisdn%type, amount in number);
    PROCEDURE update_sms(p_cust_id in customer.cust_id%type, p_msisdn in customer.msisdn%type, amount in number);
    PROCEDURE update_lvl_money(p_cust_id in customer.cust_id%type, p_msisdn in customer.msisdn%type, p_package_id in package.package_id%type ,amount in number);
END PACKAGE_DMLOPERATIONS;

create or replace PACKAGE BODY PACKAGE_DMLOPERATIONS IS 
PROCEDURE update_minutes(p_cust_id in customer.cust_id%type, p_msisdn in customer.msisdn%type, amount in number) IS
    BEGIN
    COMMIT;
        MERGE INTO BALANCE b
            USING (
                SELECT CUST_ID FROM CUSTOMER WHERE MSISDN = p_msisdn AND cust_id = p_cust_id 
            )s
            ON (b.cust_ID = s.cust_ID)
        WHEN MATCHED THEN
        UPDATE SET  b.bal_lvl_minutes = b.bal_lvl_minutes + amount ;
        COMMIT;
    END;

    PROCEDURE update_data(p_cust_id in customer.cust_id%type, p_msisdn in customer.msisdn%type, amount in number) IS
    BEGIN
    COMMIT;
        MERGE INTO BALANCE b
            USING (
                SELECT cust_id FROM customer WHERE MSISDN = p_msisdn AND cust_id = p_cust_id 
            )s
            ON (b.CUST_ID = s.CUST_ID)
        WHEN MATCHED THEN
        UPDATE SET b.bal_lvl_data = b.bal_lvl_data + amount ;
        COMMIT;
    END;

    PROCEDURE update_sms(p_cust_id in customer.cust_id%type, p_msisdn in customer.msisdn%type, amount in number) IS
    BEGIN
    COMMIT;
        MERGE INTO BALANCE b
            USING (
                SELECT CUST_ID FROM CUSTOMER WHERE MSISDN = p_msisdn AND cust_id = p_cust_id 
            )s
            ON (b.CUST_ID = s.CUST_ID)
        WHEN MATCHED THEN
        UPDATE SET b.bal_lvl_sms = b.bal_lvl_sms + amount;
        COMMIT;
    END;
    
    PROCEDURE update_lvl_money(p_cust_id in customer.cust_id%type, p_msisdn in customer.msisdn%type, p_package_id in package.package_id%type ,amount in number) IS
    BEGIN
    COMMIT;
        MERGE INTO BALANCE b
            USING (
                SELECT CUST_ID FROM CUSTOMER WHERE MSISDN = p_msisdn AND cust_id = p_cust_id
            )s
            ON (b.CUST_ID = s.CUST_ID)
        WHEN MATCHED THEN
        UPDATE SET b.bal_lvl_money = b.bal_lvl_money - amount;
        COMMIT;
    END;
END PACKAGE_DMLOPERATIONS;

/************************************************
*                                               *
*    CREATING PACKAGE FOR PACKAGE PROCEDURES    *
*                                               *
************************************************/

create or replace PACKAGE PACKAGE_PACKAGE IS
    PROCEDURE get_all_packages(recordset OUT SYS_REFCURSOR);
    PROCEDURE insert_package(P_PACKAGE_NAME IN PACKAGE.PACKAGE_NAME%TYPE, P_AMOUNT_MINUTES IN PACKAGE.AMOUNT_MINUTES%TYPE, P_AMOUNT_DATA IN PACKAGE.AMOUNT_DATA%TYPE, 
                            P_AMOUNT_SMS IN PACKAGE.AMOUNT_SMS%TYPE, P_PERIOD IN PACKAGE.PERIOD%TYPE,P_PACKAGE_ID IN PACKAGE.PACKAGE_ID%TYPE);
    PROCEDURE create_package(P_PACKAGE_ID IN PACKAGE.PACKAGE_ID%TYPE,P_PACKAGE_NAME IN PACKAGE.PACKAGE_NAME%TYPE,P_PRICE IN PACKAGE.PRICE%TYPE,
                            P_AMOUNT_MINUTES IN PACKAGE.AMOUNT_MINUTES%TYPE,P_AMOUNT_DATA IN PACKAGE.AMOUNT_DATA%TYPE,
                            P_AMOUNT_SMS IN PACKAGE.AMOUNT_SMS%TYPE,P_PERIOD IN PACKAGE.PERIOD%TYPE);
END package_package;

create or replace PACKAGE BODY PACKAGE_PACKAGE IS
    PROCEDURE get_all_packages(recordset OUT SYS_REFCURSOR) IS 
        BEGIN
        open recordset for
            SELECT package.package_id , package.package_name FROM PACKAGE;
            COMMIT;
        END;
    PROCEDURE insert_package(P_PACKAGE_NAME IN PACKAGE.PACKAGE_NAME%TYPE, P_AMOUNT_MINUTES IN PACKAGE.AMOUNT_MINUTES%TYPE, P_AMOUNT_DATA IN PACKAGE.AMOUNT_DATA%TYPE, 
                            P_AMOUNT_SMS IN PACKAGE.AMOUNT_SMS%TYPE, P_PERIOD IN PACKAGE.PERIOD%TYPE,P_PACKAGE_ID IN PACKAGE.PACKAGE_ID%TYPE) IS
        BEGIN
            INSERT INTO PACKAGE (PACKAGE_ID,PACKAGE_NAME,AMOUNT_MINUTES,AMOUNT_DATA,AMOUNT_SMS,PERIOD) 
                    VALUES (P_PACKAGE_ID, P_PACKAGE_NAME, P_AMOUNT_MINUTES, P_AMOUNT_DATA * 1024, P_AMOUNT_SMS, P_PERIOD);
            COMMIT;
        END;
        
    PROCEDURE create_package(P_PACKAGE_ID IN PACKAGE.PACKAGE_ID%TYPE,P_PACKAGE_NAME IN PACKAGE.PACKAGE_NAME%TYPE,P_PRICE IN PACKAGE.PRICE%TYPE,
                            P_AMOUNT_MINUTES IN PACKAGE.AMOUNT_MINUTES%TYPE,P_AMOUNT_DATA IN PACKAGE.AMOUNT_DATA%TYPE,
                            P_AMOUNT_SMS IN PACKAGE.AMOUNT_SMS%TYPE,P_PERIOD IN PACKAGE.PERIOD%TYPE) IS
        BEGIN
            INSERT INTO PACKAGE (package_id,package_name,price,amount_minutes,amount_data,amount_sms,period) 
               VALUES(P_PACKAGE_ID,P_PACKAGE_NAME,P_PRICE,P_AMOUNT_MINUTES,P_AMOUNT_DATA,P_AMOUNT_SMS,P_PERIOD);
            COMMIT;
        END;
END package_package;
    

/************************************************
*                                               *
*    CREATING PACKAGE FOR BALANCE PROCEDURES    *
*                                               *
************************************************/

create or replace PACKAGE package_balance IS
    PROCEDURE get_balance(P_MSISDN IN CUSTOMER.MSISDN%TYPE, recordset OUT SYS_REFCURSOR);
    FUNCTION get_balance_id RETURN NUMBER;
    FUNCTION get_partition_id RETURN NUMBER;
    PROCEDURE create_balance(S_BALANCE_ID IN BALANCE.BALANCE_ID%TYPE,S_CUST_ID IN BALANCE.CUST_ID%TYPE,
                             P_PARTITION_ID IN BALANCE.PARTITION_ID%TYPE, v_package_id IN BALANCE.PACKAGE_ID%TYPE,
                             P_BAL_MONEY IN BALANCE.BAL_LVL_MONEY%TYPE);
END package_balance;

create or replace PACKAGE BODY package_balance IS
    PROCEDURE get_balance(P_MSISDN IN CUSTOMER.MSISDN%TYPE, recordset OUT SYS_REFCURSOR) IS
        BEGIN
            Open recordset for
            SELECT BAL.* FROM BALANCE BAL INNER JOIN CUSTOMER CUST ON BAL.CUST_ID = CUST.CUST_ID WHERE CUST.MSISDN = P_MSISDN;
            COMMIT;
        END;

    FUNCTION get_balance_id RETURN NUMBER 
    AS
        u_id NUMBER;
    BEGIN 
        u_id := BALANCE_ID_SEQUENCE.nextval;
        COMMIT;
    RETURN u_id ;
    END get_balance_id;    

    FUNCTION get_partition_id RETURN NUMBER 
    AS
        u_id NUMBER;
    BEGIN 
        u_id := PARTITION_ID_SEQUENCE.nextval;
        COMMIT;
    RETURN u_id ;
    END get_partition_id;    


    PROCEDURE create_balance(S_BALANCE_ID IN BALANCE.BALANCE_ID%TYPE,S_CUST_ID IN BALANCE.CUST_ID%TYPE,
                             P_PARTITION_ID IN BALANCE.PARTITION_ID%TYPE, v_package_id IN BALANCE.PACKAGE_ID%TYPE,
                             P_BAL_MONEY IN BALANCE.BAL_LVL_MONEY%TYPE) IS
        BEGIN 
            INSERT INTO BALANCE (balance_id,package_id,cust_id,partition_id,bal_lvl_minutes, bal_lvl_sms, bal_lvl_data,sdate,edate,bal_lvl_money) 
               VALUES(S_BALANCE_ID,v_package_id,S_CUST_ID,P_PARTITION_ID, default, default, default, SYSDATE, SYSDATE,P_BAL_MONEY);
            COMMIT;
        END;  
END package_balance;

commit;
