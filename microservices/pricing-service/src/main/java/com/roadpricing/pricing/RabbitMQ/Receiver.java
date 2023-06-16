package com.roadpricing.pricing.RabbitMQ;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roadpricing.pricing.Model.Pricing;
import com.roadpricing.pricing.Repo.PricingRepo;
import com.roadpricing.pricing.Service.PricingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Component
public class Receiver {

    @Autowired
    ObjectMapper objectMapper;
    private CountDownLatch latch = new CountDownLatch(1);

    private final Logger logger = LoggerFactory.getLogger(Receiver.class);
    @Autowired
    private PricingRepo repo;
    @Autowired
    private PricingService service;

    public void receiveTraveldata(String message) {
        Pricing pricing = null;
        try {
            pricing = objectMapper.readValue(message, Pricing.class);
            logger.info("Received Pricing: " + message);
        } catch (JsonProcessingException e) {
            logger.error("ERROR: " + e);
        }
        repo.save(pricing);
        String routeId = pricing.getRouteId();
        String countryCode = pricing.getCountryCode();
        BigDecimal price = service.getSegmentPrice(pricing.getDistance(), pricing.getRoadType(), pricing.getFuelType(), pricing.getVehicleType());
        Boolean inProgress = pricing.getInProgress();
        Double startLat = pricing.getStartLat();
        Double startLon = pricing.getStartLon();
        Double endLat = pricing.getEndLat();
        Double endLon = pricing.getEndLon();
        String time = pricing.getTime();
        String roadType = pricing.getRoadType();
        String roadName = pricing.getRoadName();
        service.postToInvoice(routeId, countryCode, price, startLat, startLon, endLat, endLon, time, roadName,roadType, inProgress);

        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
