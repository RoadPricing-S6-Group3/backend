package com.roadpricing.tracking.controller;

import com.roadpricing.tracking.model.RouteModel;
import com.roadpricing.tracking.service.RouteService;
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
        return ResponseEntity.ok().body(routeModel);
    }
}
