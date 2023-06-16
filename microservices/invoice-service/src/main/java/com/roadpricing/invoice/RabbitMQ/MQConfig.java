package com.roadpricing.invoice.RabbitMQ;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MQConfig {
    static final String queueName = "invoice_queue";
    static final String exchangeName = "invoice_exchange";
    static final String routingKey = "invoice_routingkey";

    @Bean
    public Queue invoiceQueue(){
        return new Queue(queueName, true);
    }

    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(exchangeName);
    }

    @Bean
    public Binding binding (Queue queue, TopicExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }
}
