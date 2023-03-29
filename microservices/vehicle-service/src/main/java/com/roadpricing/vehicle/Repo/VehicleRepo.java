package com.roadpricing.vehicle.Repo;

import com.roadpricing.vehicle.Model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepo extends JpaRepository<Vehicle, Long> {

    Optional<Vehicle>findById(Long id);
    Optional<Vehicle>findByLicense(String license);

    List<Vehicle>findAllByOwnerId(Long ownerId);

    List<Vehicle>findAllByVehicleModel(String vehicleModel);

    List<Vehicle>findAllByFuelType(String fuelType);
}
