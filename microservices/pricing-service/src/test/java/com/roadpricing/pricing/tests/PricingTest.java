package com.roadpricing.pricing.tests;

import com.roadpricing.pricing.Model.Pricing;
import com.roadpricing.pricing.Service.PricingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PricingTest {
    private PricingService service = new PricingService();

    @Test
    void getFirstCharacter(){
        // Arrange
        String vehicleType = "M246";
        String roadType = "A28";

        // Act
        String firstCharacter1 = service.getFirstCharacter(vehicleType);
        String firstCharacter2 = service.getFirstCharacter(roadType);

        // Assert
        Assertions.assertEquals("M", firstCharacter1);
        Assertions.assertEquals("A", firstCharacter2);
    }

    @Test
    void getVehiclePrice(){
        // Arrange
        String vehicleType = "M246";

        // Act
        float price = service.getVehiclePrice(vehicleType);

        // Assert
        Assertions.assertEquals(1.1f, price);
    }

    @Test
    void getFuelPrice(){
        // Arrange
        String fuelType = "Gas";

        // Act
        float price = service.getFuelPrice(fuelType);

        // Assert
        Assertions.assertEquals(1.4f, price);
    }

    @Test
    void getRoadPrice(){
        // Arrange
        String roadType = "A28";

        // Act
        float price = service.getRoadPrice(roadType);

        // Assert
        Assertions.assertEquals(1.2f, price);
    }

    @Test
    void getTotalPrice(){
        // Arrange
        List<Pricing> travelData = new ArrayList<>();
        Pricing record1 = new Pricing();
        record1.setRouteId("Test");
        record1.setVehicleType("M");
        record1.setFuelType("Biofuel");
        record1.setRoadType("A");
        record1.setDistance(BigDecimal.valueOf(3000.00d));
        travelData.add(record1);
        Pricing record2 = new Pricing();
        record2.setRouteId("Test");
        record2.setVehicleType("M");
        record2.setFuelType("Biofuel");
        record2.setRoadType("N");
        record2.setDistance(BigDecimal.valueOf(6000.00d));
        travelData.add(record2);

        // Act
        BigDecimal price = service.getTotalPrice(travelData);

        // Assert
        Assertions.assertEquals(BigDecimal.valueOf(12.34), price);
    }

    @Test
    void testMethodStructure(){
        // Arrange
        // Act
        // Assert
    }
}
