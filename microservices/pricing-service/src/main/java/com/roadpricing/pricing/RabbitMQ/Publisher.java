package com.roadpricing.pricing.RabbitMQ;

import com.roadpricing.pricing.Dto.PriceDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.concurrent.TimeUnit;

@Component
public class Publisher {
    private final RabbitTemplate rabbitTemplate;
    private final Receiver receiver;
    Logger logger = LoggerFactory.getLogger(Publisher.class);

    @Autowired
    ObjectMapper objectMapper;

    public Publisher(Receiver receiver, RabbitTemplate rabbitTemplate) {
        this.receiver = receiver;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendData(PriceDto dto) {
//        rabbitTemplate.convertAndSend(Config.exchangeName2, Config.routingKey2, dto);
        try{
            String message = objectMapper.writeValueAsString(dto);
            rabbitTemplate.convertAndSend(Config.exchangeName2, Config.routingKey2, message);
            logger.info("[ ✨ ] " + "Sending price dto to Queue: " + message + " [ ✨ ]");
        }catch(Exception e){
            System.out.println("Error: " + e);
        }
    }
}
