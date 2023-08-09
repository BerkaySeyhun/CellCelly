package com.i2icellcelly.DGW.RestApi;

import com.i2icellcelly.DGW.Business.ISubscriberService;
import com.i2icellcelly.DGW.Common.DGWLogger;
import com.i2icellcelly.DGW.DataAccess.ISubscriberDal;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
public class SubscribersController {

    ISubscriberDal subscriberDal;
    ISubscriberService subscriberService;
    JSONParser parser = new JSONParser();

    @Autowired
    public SubscribersController(ISubscriberDal sDal, ISubscriberService sSer){
        subscriberDal = sDal;
        subscriberService = sSer;
    }

    @PostMapping("/generateTraffic")
    public String generateTraffic(@RequestBody String message) {
        DGWLogger.printInfoLogs("Generating new traffic");
        DGWLogger.printInfoLogs(message);
        try{
            JSONObject message1 = (JSONObject) parser.parse(message);
            subscriberService.generateTraffic(message1);
        } catch (Exception e){
            e.printStackTrace();
        }
        return "Traffic generated";
    }
}
