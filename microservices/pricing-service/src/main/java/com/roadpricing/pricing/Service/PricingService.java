package com.roadpricing.pricing.Service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PricingService {

    enum VehicleTypes{
        motor,
        car,
        truck
    }

    public float getVehiclePrice(String type){
        VehicleTypes vehicleType = VehicleTypes.valueOf(type);
        switch(vehicleType){
            case motor:
                return 0.25f;
            case car:
                return 0.5f;
            case truck:
                return 0.75f;
        }
        return 0f;
    }

}
