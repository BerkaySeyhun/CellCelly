
package com.CellCelly.MiddleWare.Controllers;

import com.CellCelly.MiddleWare.DataAccesLayers.CustomerRepository;
import com.CellCelly.MiddleWare.DataAccesLayers.PackageRepository;
import com.CellCelly.MiddleWare.Entities.CreateCustomerEntity;
import com.CellCelly.MiddleWare.Entities.CreateCustomerEntityAndroid;
import com.CellCelly.MiddleWare.Entities.Customer;
import com.CellCelly.MiddleWare.Hazelcast.MwOperations.HazelcastMWOperation;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.voltdb.client.NoConnectionsException;
import org.voltdb.client.ProcCallException;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin
public class CustomerController {
    private CustomerRepository customerRepository = new CustomerRepository();
    private PackageRepository PackageRepository = new PackageRepository();
    
    
    @GetMapping("/getall")
    public List<Customer> getAll() throws ClassNotFoundException, SQLException{
        return customerRepository.getAll();
    }
    
    @GetMapping("/get/{MSISDN}")
    public List<Customer> getCustomer(@PathVariable String MSISDN) throws IOException, ProcCallException{ 
        return customerRepository.getCustomer(MSISDN);
    }
 
    
    @GetMapping("/userpackage/{MSISDN}")
    public String getUserPackage(@PathVariable String MSISDN) throws ClassNotFoundException, SQLException{
        return PackageRepository.getUserPackage(MSISDN); 
    }
    
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody Map<String,String> registerCustomerRequestBody) throws ClassNotFoundException, SQLException, IOException, NoConnectionsException, ProcCallException{
       //request body must have packageId info and user infos
        Long packageId = Long.valueOf(registerCustomerRequestBody.get("PACKAGE_ID"));
        String MSISDN = registerCustomerRequestBody.get("MSISDN");
        String NAME = registerCustomerRequestBody.get("NAME");
        String SURNAME = registerCustomerRequestBody.get("SURNAME");
        String EMAIL = registerCustomerRequestBody.get("EMAIL");
        String PASSWORD = registerCustomerRequestBody.get("PASSWORD");
        String SECURITY_KEY = registerCustomerRequestBody.get("SECURITY_KEY");
        
        //create user with model
        CreateCustomerEntity newCustomer = new CreateCustomerEntity();
        newCustomer.setMsisdn(MSISDN);
        newCustomer.setName(NAME);
        newCustomer.setSurname(SURNAME);
        newCustomer.setEmail(EMAIL);
        newCustomer.setPassword(PASSWORD);
        newCustomer.setSecurityKey(SECURITY_KEY);
        newCustomer.setPackageId(packageId);
         
        //insert ot voltdb
        customerRepository.VoltdbCreateCustomer(newCustomer);
        //insert to hazelcast
        HazelcastMWOperation.put(MSISDN,Long.toString(customerRepository.getPartitionId()));
        //insert to oracle
        customerRepository.OracleCreateCustomer(newCustomer);
        
        return new ResponseEntity<>("Customer registration is fully completed.", HttpStatus.CREATED);
    }
    
    @PostMapping("/android/register")
    public ResponseEntity registerAndroid(@RequestBody CreateCustomerEntityAndroid customerEntity) throws ClassNotFoundException, SQLException, IOException, NoConnectionsException, ProcCallException{
       //request body must have packageId info and user infos
           
    String MSISDN = customerEntity.getMsisdn();
        System.out.println(MSISDN);
    long packageId = Long.parseLong(customerEntity.getPackageId());
    String NAME = customerEntity.getName();
    String SURNAME = customerEntity.getSurname();
    String EMAIL = customerEntity.getEmail();
    String PASSWORD = customerEntity.getPassword();
    String SECURITY_KEY = customerEntity.getSecurityKey();
        
        //create user with model
        CreateCustomerEntity newCustomer = new CreateCustomerEntity();
        newCustomer.setMsisdn(MSISDN);
        newCustomer.setName(NAME);
        newCustomer.setSurname(SURNAME);
        newCustomer.setEmail(EMAIL);
        newCustomer.setPassword(PASSWORD);
        newCustomer.setSecurityKey(SECURITY_KEY);
        newCustomer.setPackageId(packageId);
         
        //insert ot voltdb
        customerRepository.VoltdbCreateCustomer(newCustomer);
        //insert to hazelcast
        HazelcastMWOperation.put(MSISDN,Long.toString(customerRepository.getPartitionId()));
        //insert to oracle
        customerRepository.OracleCreateCustomer(newCustomer);
        
        return new ResponseEntity<>("Customer registration is fully completed.", HttpStatus.CREATED);
    }
}
