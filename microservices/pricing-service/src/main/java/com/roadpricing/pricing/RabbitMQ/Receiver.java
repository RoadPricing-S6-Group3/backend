package com.roadpricing.pricing.RabbitMQ;

import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class Receiver {

    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveTraveldata(String data) {
        System.out.println("Received <" + data + ">");
        // repo.save(data);
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
