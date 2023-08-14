package com.CellCelly.MiddleWare;

import com.CellCelly.MiddleWare.Hazelcast.MwOperations.HazelcastMWOperation;
import java.sql.SQLException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 *
 * @author RamazanFirat
 */

@SpringBootApplication
public class MiddleWareApplication {

    

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
                
         // HazelcastMWOperation.remove("5375592052");
        
               
		SpringApplication.run(MiddleWareApplication.class, args);
                
                
                
	}
             
}
