package com.roadpricing.pricing.Service;

import com.roadpricing.pricing.Model.Pricing;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

@Service
public class PricingService {

    private static final DecimalFormat df = new DecimalFormat("#,##");

    enum VehicleTypes{
        L, L1, L2, L3, L4, L5, L6, L7, M, M1, M2, M3, N, N1, N2, N3, O, O1, O2, O3, O4, T, R, S, G
    }

    enum FuelTypes{
        Alternative, Biofuel, Diesel, Ethanol, Gas, Oil
    }

    enum RoadTypes{
        A, N
    }

    public float getVehiclePrice(String type){
        VehicleTypes vehicleType = VehicleTypes.valueOf(type);
        switch(vehicleType){
            case L:
            case L1:
            case L2:
            case L3:
            case L4:
            case L5:
            case L6:
            case L7:
                return 1.0f;
            case M:
            case M1:
            case M2:
            case M3:
                return 1.1f;
            case N:
            case N1:
            case N2:
            case N3:
                return 1.2f;
            case O:
            case O1:
            case O2:
            case O3:
            case O4:
                return 1.3f;
            case T:
            case R:
            case S:
            case G:
                return 1.4f;
        }
        return 0f;
    }

    public float getFuelPrice(String type){
        FuelTypes fuelType = FuelTypes.valueOf(type);
        switch(fuelType){
            case Alternative:
                return 1.0f;
            case Biofuel:
                return 1.1f;
            case Diesel:
                return 1.2f;
            case Ethanol:
                return 1.3f;
            case Gas:
                return 1.4f;
            case Oil:
                return 1.5f;
        }
        return 0f;
    }

    public float getRoadPrice(String type){
        RoadTypes roadType = RoadTypes.valueOf(type);
        switch(roadType){
            case A:
                return 1.2f;
            case N:
                return 1.1f;
        }
        return 0f;
    }

    public BigDecimal getTotalPrice(List<Pricing> travelData){
        float total = 0f;
        for(Pricing record : travelData) {
            float price = (record.getDistance().floatValue() / 1000) * getRoadPrice(record.getRoadType()) * getFuelPrice(record.getFuelType()) * getVehiclePrice(record.getVehicleType());
            total += price;
        }
        BigDecimal bigDecimal = new BigDecimal(total);
        BigDecimal roundedValue = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
        return roundedValue;
    }

    public List<Pricing> getAllById(int id){
        return null; // repo.getAllById(id);
    }
}
