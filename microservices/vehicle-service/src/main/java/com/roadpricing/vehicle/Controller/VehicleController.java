package com.roadpricing.vehicle.Controller;

import com.roadpricing.vehicle.Model.Vehicle;
import com.roadpricing.vehicle.Service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/vehicle")
public class VehicleController {

    @Autowired
    VehicleService service;

    @GetMapping()
    public ResponseEntity<List<Vehicle>> getAllVehicles (){
        try{
            List<Vehicle> vehicles = service.findAll();
            return ResponseEntity.ok().body(vehicles);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<Vehicle>getVehicleById(@PathVariable(value = "id")Long id){
        try{
            Vehicle vehicle =service.findById(id);
            return ResponseEntity.ok().body(vehicle);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }
    @GetMapping("/license/{license}")
    public ResponseEntity<Vehicle>getVehicleById(@PathVariable(value = "license")String license){
        try{
            Vehicle vehicle = service.findByLicense(license);
            return ResponseEntity.ok().body(vehicle);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<Vehicle>>getVehicleByOwnerId(@PathVariable(value = "ownerId")Long ownerId){
        try{
            List<Vehicle> vehicles = service.findByOwnerId(ownerId);
            return ResponseEntity.ok().body(vehicles);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }
    @GetMapping("/model/{model}")
    public ResponseEntity<List<Vehicle>>getVehicleByVehicleModel(@PathVariable(value = "model")String  model){
        try{
            List<Vehicle> vehicles = service.findByVehicleModel(model);
            return ResponseEntity.ok().body(vehicles);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }
    @GetMapping("/fuel/{fuelType}")
    public ResponseEntity<List<Vehicle>>getVehicleByFuelType(@PathVariable(value = "fuelType")String fuelType){
        try{
            List<Vehicle> vehicles = service.findByFuelType(fuelType);
            return ResponseEntity.ok().body(vehicles);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }
    @PostMapping("/save")
    public ResponseEntity<Vehicle> saveVehicle(@RequestBody Vehicle vehicle){
        try{
            service.saveVehicle(vehicle);
            return ResponseEntity.ok().body(vehicle);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
    @PutMapping("/update")
    public ResponseEntity<Vehicle> updateVehicle(@RequestBody Vehicle vehicle){
        try{
            service.updateVehicle(vehicle);
            return ResponseEntity.ok().body(vehicle);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteVehicle(@PathVariable(value = "id") Long id){
        try{
            service.deleteVehicle(id);
            return ResponseEntity.ok().build();
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
