package com.roadpricing.invoice.RabbitMQ;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;

public class MQConfig {
    static final String queueName = "invoice_queue";
    static final String exchangeName = "ivoice_exchange";

    @Bean
    public Queue invoiceQueue(){
        return new Queue(queueName, false);
    }

    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(exchangeName);
    }

    @Bean
    public Binding binding (Queue queue, TopicExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("invoice_routingkey");
    }

}
