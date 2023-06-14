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
        if(!pricing.getInProgress()) {
            List<Pricing> pricingData = repo.findAllByRouteId(pricing.getRouteId());
            BigDecimal price = service.getTotalPrice(pricingData);
            String countryCode = pricingData.get(0).getCountryCode();
            service.postTotalPrice(price, countryCode);
        }

        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
