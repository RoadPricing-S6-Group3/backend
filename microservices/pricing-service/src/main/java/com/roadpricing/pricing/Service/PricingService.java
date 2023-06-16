package com.roadpricing.pricing.Service;

import com.roadpricing.pricing.Dto.*;
import com.roadpricing.pricing.Model.Pricing;
import com.roadpricing.pricing.RabbitMQ.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

@Service
public class PricingService {

    @Autowired
    private Publisher publisher;

    private static final DecimalFormat df = new DecimalFormat("#,##");

    enum VehicleTypes{
        L, L1, L2, L3, L4, L5, L6, L7, M, M1, M2, M3, N, N1, N2, N3, O, O1, O2, O3, O4, T, R, S, G
    }

    enum FuelTypes{
        Alternative, Biofuel, Diesel, Ethanol, Gas, Oil, Petrol
    }

    enum RoadTypes{
        A, N, R
    }

    public String getFirstCharacter(String data){
        String firstCharacter = data.substring(0, 1);
        return firstCharacter;
    }

    public float getVehiclePrice(String type){
        String firstCharacter = getFirstCharacter(type);
        VehicleTypes vehicleType = VehicleTypes.valueOf(firstCharacter);
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
            case Petrol:
                return 1.6f;
        }
        return 0f;
    }

    public float getRoadPrice(String type){
        if(type.equals("N/A") != true){
            String firstCharacter = getFirstCharacter(type);
            RoadTypes roadType = RoadTypes.valueOf(firstCharacter);
            switch(roadType){
                case A:
                    return 1.2f;
                case N:
                    return 1.1f;
                case R:
                    return 1.3f;
                default:
                    return 1.6f;
            }
        }
        else {
            return 2.0f;
        }
    }

    public BigDecimal getSegmentPrice(BigDecimal distance, String roadType, String fuelType, String vehicleType){
        float segment = 0f;
        float price = (distance.floatValue() / 1000) * getRoadPrice(roadType) * getFuelPrice(fuelType) * getVehiclePrice(vehicleType);
        segment += price;
        BigDecimal bigDecimal = new BigDecimal(segment);
        BigDecimal roundedValue = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
        return roundedValue;
    }

    public void postToInvoice(String routeId, String countryCode, BigDecimal price, Double startLat, Double startLon, Double endLat, Double endLon, String time, String roadName, String roadType , Boolean inProgress){
        if(roadName.isEmpty() || roadName.isBlank() || roadType == null){
            roadName = "N/A";
        }
        if(roadType.isEmpty() || roadType.isBlank() || roadType == null){
            roadType = "N/A";
        }
        SegmentWay way = new SegmentWay();
        way.setId(roadName);
        SegmentStart start = new SegmentStart();
        start.setLat(startLat);
        start.setLon(startLon);
        start.setId(roadType);
        SegmentEnd end = new SegmentEnd();
        end.setLat(endLat);
        end.setLon(endLon);
        end.setId(roadType);
        InvoiceSegment segment = new InvoiceSegment();
        segment.setPrice(price.doubleValue());
        segment.setStart(start);
        segment.setEnd(end);
        segment.setTime(time);
        segment.setWay(way);
        PriceDto dto = new PriceDto();
        dto.setRouteId(routeId);
        dto.setCountryCode(countryCode);
        dto.setSegment(segment);
        dto.setInProgress(inProgress);
        publisher.sendData(dto);
    }

    public List<Pricing> getAllById(int id){
        return null; // repo.getAllById(id);
    }
}
