package com.roadpricing.vehicle.Service;

import com.roadpricing.vehicle.Model.Vehicle;
import com.roadpricing.vehicle.Repo.VehicleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

    @Autowired
    VehicleRepo repo;

    public List<Vehicle> findAll(){
        List<Vehicle> vehicles = repo.findAll();
        return vehicles;
    }
    public Vehicle findById(Long id){
        Vehicle vehicle = repo.findById(id).orElse(null);
        return vehicle;
    }
    public Vehicle findByLicense(String license){
        Vehicle vehicle = repo.findByLicense(license).orElse(null);
        return vehicle;
    }
    public List<Vehicle> findByOwnerId(Long ownerId){
        List<Vehicle> vehicles = repo.findAllByOwnerId(ownerId);
        return vehicles;
    }

    public List<Vehicle> findByVehicleModel(String vehicleModel){
        List<Vehicle> vehicles = repo.findAllByVehicleModel(vehicleModel);
        return vehicles;
    }
    public List<Vehicle> findByFuelType(String fueltype){
        List<Vehicle> vehicles = repo.findAllByFuelType(fueltype);
        return vehicles;
    }

    public void saveVehicle(Vehicle vehicle){
        repo.save(vehicle);
    }
    public void updateVehicle(Vehicle vehicle){
        if(repo.findById(vehicle.getId())!= null){
            repo.save(vehicle);
        }
    }
    public void deleteVehicle(Long id){
        repo.deleteById(id);
    }
}
