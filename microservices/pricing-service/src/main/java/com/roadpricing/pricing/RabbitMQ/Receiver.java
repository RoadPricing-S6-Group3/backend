package com.roadpricing.pricing.RabbitMQ;

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

    private CountDownLatch latch = new CountDownLatch(1);

    @Autowired
    private PricingRepo repo;
    private Publisher publisher;
    private PricingService service;

    public void receiveTraveldata(Pricing pricing) {
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
