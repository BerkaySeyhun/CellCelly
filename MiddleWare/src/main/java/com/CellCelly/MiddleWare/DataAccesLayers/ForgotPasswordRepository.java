
package com.CellCelly.MiddleWare.DataAccesLayers;

import com.CellCelly.MiddleWare.Crypt.Encryption;
import com.CellCelly.MiddleWare.DbConnectHelpers.OracleDbHelper;
import com.CellCelly.MiddleWare.Entities.ForgotPassword;
import com.CellCelly.MiddleWare.OtherHelpers.MailSender;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ForgotPasswordRepository {
    private Encryption encryption = new Encryption();
    public ResponseEntity forgotPassword(ForgotPassword forgotPassword) throws ClassNotFoundException, SQLException {
        String password = null;
        MailSender mailSender = new MailSender();
        
        Connection conn = OracleDbHelper.getConnection();
        CallableStatement cstmt = conn.prepareCall("{? = call package_customer.forget_password(?, ?)}");

        cstmt.registerOutParameter(1, java.sql.Types.NVARCHAR);
        cstmt.setString(2, forgotPassword.getEMAIL());
        cstmt.setString(3, forgotPassword.getSECURITY_KEY());
        cstmt.execute();
        
        password = cstmt.getString(1);
        System.out.println(password);
        conn.close();
        if(password != null){
            
            password = encryption.Decryption(password);
            String toEmail = forgotPassword.getEMAIL();
            String subject = "CellCelly Password";
            String body = "Your password is " + password;
           
            mailSender.sendEmail(toEmail, subject, body);
            
            return new ResponseEntity<>("E-mail is sent", HttpStatus.OK);
        }else
            
             return new ResponseEntity<>("User not Found", HttpStatus.BAD_REQUEST);
    }
    
}
