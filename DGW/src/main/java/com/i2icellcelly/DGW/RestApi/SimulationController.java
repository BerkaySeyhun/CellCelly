package com.i2icellcelly.DGW.RestApi;

import com.i2icellcelly.DGW.Business.ITrafficService;
import com.i2icellcelly.DGW.Common.DGWLogger;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * The REST API for the simulator to generate the traffic.
 * The endpoint for the API is /api/generateTraffic.
 */
@RequestMapping("/api")
@RestController
public class SimulationController {

    ITrafficService subscriberService;
    JSONParser parser = new JSONParser();

    /**
     * Constructor for SimulationControl.
     * @param sSer   The Traffic Service used for sending the messages to the OCS.
     */
    @Autowired
    public SimulationController(ITrafficService sSer){
        subscriberService = sSer;
    }

    /**
     * The endpoint for the API to receive simulated traffic.
     * @param diagramMessage    Since the endpoint is open to POST requests, diagramMessage is the String
     *                          that is sent in the POST request body. The diagramMessage must be sent in a
     *                          valid JSON format, or else the function terminates.
     * @return                  Returns a response String that specifies the success of the traffic
     *                          generation process.
     */
    @PostMapping("/generateTraffic")
    public String generateTraffic(@RequestBody String diagramMessage) {
        DGWLogger.printInfoLogs("Generating new traffic");
        DGWLogger.printInfoLogs(diagramMessage);
        try{
            JSONObject jsonDiagramMessage = (JSONObject) parser.parse(diagramMessage);
            subscriberService.generateTraffic(jsonDiagramMessage);
        } catch (Exception e){
            e.printStackTrace();
            DGWLogger.printWarningLogs("Error parsing JSON, please provide a valid String.");
            return "Error parsing JSON, please provide a valid String.";
        }
        return "Traffic generated";
    }
}
