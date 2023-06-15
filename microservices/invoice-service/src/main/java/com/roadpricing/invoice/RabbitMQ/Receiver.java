package com.roadpricing.invoice.RabbitMQ;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roadpricing.invoice.Dto.PriceDto;
import com.roadpricing.invoice.Service.InvoiceService;
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
    @Autowired
    InvoiceService service;

    private final Logger logger = LoggerFactory.getLogger(Receiver.class);

    public void receiveData(String message) {
        System.out.println("Received message: " + message);
        PriceDto dto = null;
        try {
            dto = objectMapper.readValue(message, PriceDto.class);
            logger.info("Received dto: " + message);
        } catch (JsonProcessingException e) {
            logger.error("ERROR: " + e);
        }
        service.sendInvoiceToCountry(dto);
    }
}
