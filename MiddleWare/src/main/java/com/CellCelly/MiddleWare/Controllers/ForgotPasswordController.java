
package com.CellCelly.MiddleWare.Controllers;

import com.CellCelly.MiddleWare.DataAccesLayers.ForgotPasswordRepository;
import com.CellCelly.MiddleWare.Entities.ForgotPassword;
import java.sql.SQLException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer/forgotpassword")
@CrossOrigin
public class ForgotPasswordController {
    ForgotPasswordRepository forgotPasswordRepository = new ForgotPasswordRepository();
    
    @GetMapping("/{MSISDN}/{EMAIL}/{SECURITY_KEY}")
    public ResponseEntity forgotPassword(@PathVariable String MSISDN,@PathVariable String EMAIL,@PathVariable String SECURITY_KEY) throws ClassNotFoundException, SQLException{
        ForgotPassword forgotPassword = new ForgotPassword(MSISDN,EMAIL,SECURITY_KEY);
        //System.out.println("forgotPasswordmail : "+forgotPassword.getEMAIL());
        return forgotPasswordRepository.forgotPassword(forgotPassword);
    }
    
    
}
