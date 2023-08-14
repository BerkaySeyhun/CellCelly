package com.CellCelly.MiddleWare.Controllers;

import com.CellCelly.MiddleWare.DataAccesLayers.CustomerRepository;
import com.CellCelly.MiddleWare.Entities.Login;
import com.CellCelly.MiddleWare.Hazelcast.MwOperations.HazelcastMWOperation;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/api/customer/login")
@CrossOrigin
public class LoginController {
    CustomerRepository customerRepository = new CustomerRepository();
    
    @GetMapping("/{MSISDN}/{PASSWORD}")
    public ResponseEntity loginCheck(@PathVariable String MSISDN, @PathVariable String PASSWORD)throws SQLException, ClassNotFoundException {
        Login login = new Login(MSISDN, PASSWORD);
       return customerRepository.login(login);
    }
    
     @GetMapping(value = "/android/{MSISDN}/{password}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity loginCheckAndroid(@PathVariable String MSISDN, @PathVariable String password) throws SQLException, ClassNotFoundException {
         Login login = new Login(MSISDN, password);
        return customerRepository.loginCheckAndroid(login);
    }


} 
