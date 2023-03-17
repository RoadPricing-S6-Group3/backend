package com.roadpricing.vehicle.Controller;

import com.roadpricing.vehicle.Model.Vehicle;
import com.roadpricing.vehicle.Service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vehicle")
public class VehicleController {

    @Autowired
    VehicleService service;

    @GetMapping
    public ResponseEntity<String> getAllVehicles (){
        try{
            String vehicles = "This will be the vehicles list";
            return ResponseEntity.ok().body(vehicles);
        }
        catch (Exception e){
            String warn = "Could not find Cars or request is invalid";
            return ResponseEntity.badRequest().body(warn);
        }
    }
}
