
package com.CellCelly.MiddleWare.Controllers;

import com.CellCelly.MiddleWare.DataAccesLayers.PackageRepository;
import com.CellCelly.MiddleWare.Entities.Package;
import com.CellCelly.MiddleWare.Entities.PackageIdName;

import java.io.IOException;

import java.sql.SQLException;

import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.voltdb.client.ProcCallException;




@RestController
@RequestMapping("/api/package")
@CrossOrigin
public class PackageController {
    private final PackageRepository packageRepository = new PackageRepository();
    

    @GetMapping("/getall")
    public List<Package> getAllPackages() throws SQLException, ClassNotFoundException{
        return packageRepository.getAllPackages();
    }
    
    @GetMapping("/getall/id/name")
    public List<PackageIdName> getAllPackagesIdName() throws SQLException, ClassNotFoundException{
        
        return packageRepository.getAllPackagesIdName();
        
    }
    
    @GetMapping("/getuserpackage/{MSISDN}")
    public List<Package> getPackageByMSISDN(@PathVariable String MSISDN) throws IOException, ProcCallException {
        return packageRepository.getPackageByMSISDN(MSISDN);
    }
    
    @GetMapping(value = "/getuserpackage/android/{MSISDN}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Package packageInfoInObject(@PathVariable String MSISDN) throws IOException, ProcCallException {
        return packageRepository.getPackageByMSISDNinObject(MSISDN);
    }

    
    
    
}
