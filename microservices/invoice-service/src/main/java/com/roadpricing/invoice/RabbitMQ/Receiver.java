package com.roadpricing.invoice.RabbitMQ;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roadpricing.invoice.Model.Invoice;
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

    public void receiveData(String message) {
        System.out.println("Received message: " + message);
        //Invoice invoice = new Invoice();

        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
