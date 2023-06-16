package com.roadpricing.tracking.controller;

import com.roadpricing.tracking.dto.OutGoingRouteDTO;
import com.roadpricing.tracking.model.RouteModel;
import com.roadpricing.tracking.service.CreateOutGoingService;
import com.roadpricing.tracking.service.RouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/tracking")
public class Controller {
    @Autowired
    RouteService routeService;

    @Autowired
    CreateOutGoingService outGoingService;

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);
    @GetMapping("/coord/{coordinatesbegin}/{coordinatesend}")
    public ResponseEntity<RouteModel> createRoute(@PathVariable("coordinatesbegin")String coordinatesbegin,
                                                  @PathVariable (value = "coordinatesend")String coordinatesend){
        String cords = coordinatesbegin + ";" + coordinatesend;
        RouteModel routeModel = new RouteModel();
        try {
            routeModel = routeService.createRoute(cords);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        OutGoingRouteDTO outGoingRouteDTO = outGoingService.createOutGoingRouteDTO(routeModel);
        try{
            String response = outGoingService.sendToTravelData(outGoingRouteDTO);
            logger.info("Send route to TravelData");
        }
        catch (Exception e){
            logger.info("Error: " + e);
        }
        return ResponseEntity.ok().body(routeModel);
    }
}
