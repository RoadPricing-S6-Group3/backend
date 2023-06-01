package com.roadpricing.traveldata.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roadpricing.traveldata.dto.OutGoingRouteDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Publisher {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    ObjectMapper objectMapper;
    Logger logger = LoggerFactory.getLogger(Publisher.class);

    public void publishOutGoingRouteDTO (OutGoingRouteDTO outGoingRouteDTO){
        try{
            String message = objectMapper.writeValueAsString(outGoingRouteDTO);
            rabbitTemplate.convertAndSend(MQConfig.EXCHANGENAME, MQConfig.ROUTINGKEY, message);
            logger.info("[ ✨ ] " + "Send OutGoingRouteDTO to Queue: " + message + " [ ✨ ]");
        }
        catch (Exception e){
            logger.error("Error:" + e);
        }
    }
}
