package com.roadpricing.pricing.Controller;

import com.roadpricing.pricing.Model.Pricing;
import com.roadpricing.pricing.Service.PricingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/pricing")
public class PricingController {

    @Autowired
    PricingService service;

    @GetMapping("/vehicleType/{vehicleType}")
    public ResponseEntity<Float> getVehiclePrice(@PathVariable(value = "vehicleType")String vehicleType){
        try{
            float vehiclePrice = service.getVehiclePrice(vehicleType);
            return ResponseEntity.ok().body(vehiclePrice);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/fuelType/{fuelType}")
    public ResponseEntity<Float> getFuelPrice(@PathVariable(value = "fuelType")String fuelType){
        try{
            float fuelPrice = service.getFuelPrice(fuelType);
            return ResponseEntity.ok().body(fuelPrice);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/roadType/{roadType}")
    public ResponseEntity<Float> getRoadPrice(@PathVariable(value = "roadType")String roadType){
        try{
            float roadPrice = service.getRoadPrice(roadType);
            return ResponseEntity.ok().body(roadPrice);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<BigDecimal> get(@PathVariable(value = "id")int id){
        try{
            List<Pricing> travelData = service.getAllById(id);
            BigDecimal price = service.getTotalPrice(travelData);
            return ResponseEntity.ok().body(price);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }
}
