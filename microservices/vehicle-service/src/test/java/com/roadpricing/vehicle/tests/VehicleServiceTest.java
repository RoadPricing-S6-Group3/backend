package com.roadpricing.vehicle.tests;

import com.roadpricing.vehicle.Model.Vehicle;
import com.roadpricing.vehicle.Repo.VehicleRepo;
import com.roadpricing.vehicle.Service.VehicleService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class VehicleServiceTest {
    @Mock
    private VehicleRepo repo;

    @InjectMocks
    private VehicleService service;


    @Test
    void findById() {
        //assign
        long id = 1;
        long ownerId = 1;
        Vehicle testVehicle = new Vehicle();
        Vehicle retrievedVehicle;
        testVehicle.setId(id);
        testVehicle.setVehicleModel("test");
        testVehicle.setEfficiencyLabel("A");
        testVehicle.setFuelType("Electric");
        testVehicle.setLicense("TESTING");
        testVehicle.setOwnerId(ownerId);
        //act
        Mockito.when(repo.findById(any(Long.class))).thenReturn(Optional.of(testVehicle));
        retrievedVehicle = service.findById(id);
        //assert
        assert(retrievedVehicle == testVehicle);
    }

    @Test
    void findByLicense() {
    }

    @Test
    void findByOwnerId() {
    }

    @Test
    void findByVehicleModel() {
    }

    @Test
    void findByFuelType() {
    }

    @Test
    void saveVehicle() {
    }

    @Test
    void updateVehicle() {
    }

    @Test
    void deleteVehicle() {
    }

}