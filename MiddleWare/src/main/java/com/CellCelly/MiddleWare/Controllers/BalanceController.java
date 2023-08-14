
package com.CellCelly.MiddleWare.Controllers;


import com.CellCelly.MiddleWare.DataAccesLayers.BalanceRepository;
import com.CellCelly.MiddleWare.Entities.Balance;
import com.CellCelly.MiddleWare.Entities.RemainingBalance;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.voltdb.client.NoConnectionsException;
import org.voltdb.client.ProcCallException;




@RestController
@RequestMapping("/api/balance")
@CrossOrigin
public class BalanceController {
    private BalanceRepository balanceRepository = new BalanceRepository();
    
   @GetMapping("/getuserbalance/{MSISDN}")
    public List<RemainingBalance> getBalanceByMSISDN(@PathVariable String MSISDN) throws IOException, NoConnectionsException, ProcCallException, ClassNotFoundException, SQLException {
       return balanceRepository.getBalanceByMSISDN(MSISDN);
    } 
    
    @GetMapping(value = "/getuserbalance/android/{MSISDN}",produces= MediaType.APPLICATION_JSON_VALUE)
    public RemainingBalance getBalanceByMSISDNandroid(@PathVariable String MSISDN) throws IOException, NoConnectionsException, ProcCallException, ClassNotFoundException, SQLException {
       return balanceRepository.getBalanceByMSISDNandroid(MSISDN);
    } 
    
    @PutMapping("/updateuserdata/{CUST_ID}/{MSISDN}/{AMOUNT}")
    public ResponseEntity updateData(@PathVariable long CUST_ID,@PathVariable String MSISDN,@PathVariable long AMOUNT) throws ClassNotFoundException, SQLException{
        return balanceRepository.updateData(CUST_ID,MSISDN,AMOUNT);
    }
    
    @PutMapping("/updateuserminute/{CUST_ID}/{MSISDN}/{AMOUNT}")
    public ResponseEntity updateMinute(@PathVariable long CUST_ID,@PathVariable String MSISDN,@PathVariable long AMOUNT) throws ClassNotFoundException, SQLException{
        return balanceRepository.updateMinute(CUST_ID,MSISDN,AMOUNT);
    }
    
    @PutMapping("/updateusersms/{CUST_ID}/{MSISDN}/{AMOUNT}")
    public ResponseEntity updateSMS(@PathVariable long CUST_ID,@PathVariable String MSISDN,@PathVariable long AMOUNT) throws ClassNotFoundException, SQLException{
        return balanceRepository.updateSMS(CUST_ID,MSISDN,AMOUNT);
    }
    
    //overtariff
    @PutMapping("/updateusermoney/{CUST_ID}/{MSISDN}/{AMOUNT}")
    public ResponseEntity updateMoney(@PathVariable long CUST_ID,@PathVariable String MSISDN,@PathVariable long AMOUNT) throws ClassNotFoundException, SQLException{
        
        return balanceRepository.updateLvlMoney(CUST_ID,MSISDN,AMOUNT);
    }
    
    
    
    
}
