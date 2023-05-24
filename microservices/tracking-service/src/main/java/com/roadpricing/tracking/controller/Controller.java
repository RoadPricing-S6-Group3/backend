package com.roadpricing.tracking.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roadpricing.tracking.model.RouteModel;
import com.roadpricing.tracking.service.RouteService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/api/tracking")
public class Controller {
    @Autowired
    RouteService routeService;
    @Autowired
    ObjectMapper obejectmapper;
    @GetMapping("/coord/{coordinates}")
    public ResponseEntity<RouteModel> createRoute(@PathVariable(value= "coordinates")String coordinates){
        String cords = "5.480312336981955,51.44370749735556;5.453453331511914,51.45154959663053";
        RouteModel routeModel = new RouteModel();
        try {
            routeModel = routeService.createRoute(cords);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok().body(routeModel);
    }
}
