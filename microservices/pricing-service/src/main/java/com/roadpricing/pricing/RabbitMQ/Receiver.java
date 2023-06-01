package com.roadpricing.pricing.RabbitMQ;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roadpricing.pricing.Model.Pricing;
import com.roadpricing.pricing.Repo.PricingRepo;
import com.roadpricing.pricing.Service.PricingService;
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

    @Autowired
    private PricingRepo repo;
    private Publisher publisher;
    @Autowired
    private PricingService service;

    public void receiveTraveldata(String message) {
        Pricing pricing = null;
        try {
            pricing = objectMapper.readValue(message, Pricing.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        repo.save(pricing);
        if(!pricing.getInProgress()) {
            List<Pricing> pricingData = repo.findAllByRouteId(pricing.getRouteId());
            BigDecimal price = service.getTotalPrice(pricingData);
            publisher.sendData(price.toString());
        }

        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
