package com.roadpricing.pricing.Controller;

import com.roadpricing.pricing.Service.PricingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/pricing")
public class PricingController {

    @Autowired
    PricingService service;

    @GetMapping("/{vehicleType}")
    public ResponseEntity<Float> get(@PathVariable(value = "vehicleType")String vehicleType){
        try{
            float vehiclePrice = service.getVehiclePrice(vehicleType);
            return ResponseEntity.ok().body(vehiclePrice);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }
}
