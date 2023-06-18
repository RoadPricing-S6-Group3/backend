package com.roadpricing.tracking.controller;

import com.roadpricing.tracking.dto.OutGoingRouteDTO;
import com.roadpricing.tracking.model.RouteModel;
import com.roadpricing.tracking.service.CreateOutGoingService;
import com.roadpricing.tracking.service.RouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tracking")
public class Controller {
    @Autowired
    RouteService routeService;

    @Autowired
    CreateOutGoingService outGoingService;

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);
    @GetMapping("/coord/{coordinatesbegin}/{coordinatesend}")
    public ResponseEntity<String> createRoute(@PathVariable("coordinatesbegin")String coordinatesbegin,
                                                  @PathVariable (value = "coordinatesend")String coordinatesend,
                                                  @RequestParam("cc")String cc){
        String cords = coordinatesbegin + ";" + coordinatesend;
        RouteModel routeModel = new RouteModel();
        String response = "";
        try {
            routeModel = routeService.createRoute(cords);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        OutGoingRouteDTO outGoingRouteDTO = outGoingService.createOutGoingRouteDTO(routeModel);
        try{
            response = outGoingService.sendToTravelData(outGoingRouteDTO, cc);

            logger.info("Sent route to TravelData");
            logger.info(response);
        }
        catch (Exception e){
            logger.info("Error: " + e);
        }
        return ResponseEntity.ok().body(response);
    }
    @GetMapping("/coord/receivetest/{coordinatesbegin}/{coordinatesend}")
    public ResponseEntity<String> createTestRaw(@PathVariable("coordinatesbegin")String coordinatesbegin,
                                              @PathVariable (value = "coordinatesend")String coordinatesend){
        RouteModel routeModel = null;
        String cords = coordinatesbegin + ";" + coordinatesend;
        OutGoingRouteDTO outGoingRouteDTO = null;
        String submitRaw = "";
        try {
            routeModel = routeService.createRoute(cords);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (routeModel != null) {
            outGoingRouteDTO = outGoingService.createOutGoingRouteDTO(routeModel);
        }
        if(outGoingRouteDTO != null){
            submitRaw = outGoingService.createSubmitRaw(outGoingRouteDTO);
        }
        if(submitRaw.isEmpty() != true && submitRaw.isBlank() != true && !submitRaw.equals(null)){
            return ResponseEntity.ok().body(submitRaw);
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }

}
