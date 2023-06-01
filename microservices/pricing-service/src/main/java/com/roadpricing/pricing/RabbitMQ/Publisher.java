package com.roadpricing.pricing.RabbitMQ;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class Publisher {
    private final RabbitTemplate rabbitTemplate;
    private final Receiver receiver;

    public Publisher(Receiver receiver, RabbitTemplate rabbitTemplate) {
        this.receiver = receiver;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendData(String data) {
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(Config.exchangeName, Config.routingKey, data);
        try{
            receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        }catch(Exception e){
            System.out.println("Error: " + e);
        }
    }
}
