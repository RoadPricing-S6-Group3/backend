package com.roadpricing.invoice.RabbitMQ;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roadpricing.invoice.Dto.PriceDto;
import com.roadpricing.invoice.Service.InvoiceService;
import com.roadpricing.invoice.Service.PriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageListener {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    InvoiceService service;
    @Autowired
    PriceService priceService;
    private static final Logger logger = LoggerFactory.getLogger(MessageListener.class);

    @RabbitListener(queues = "invoice_queue")
    public void recieveMessage(String message){
        System.out.println("Received message: " + message);
        PriceDto dto = null;
        try {
            dto = objectMapper.readValue(message, PriceDto.class);
            logger.info("Received dto: " + message);
        } catch (JsonProcessingException e) {
            logger.error("ERROR: " + e);
        }
        if(dto != null && dto.getRouteId().isEmpty() != true && dto.getRouteId().isBlank() != true ){
            priceService.savePriceDto(dto);
        }
        if(dto.getInProgress() == false){
            List<PriceDto> priceDtos = priceService.findAll(dto.getRouteId());
            service.sendInvoiceToCountry(priceDtos, dto.getCountryCode());
        }
    }
}
