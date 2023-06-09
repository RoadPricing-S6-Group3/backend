package com.roadpricing.pricing.RabbitMQ;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class Config {
    static final String queueName = "traveldata_queue";
    static final String queueName2 = "invoice_queue";
    static final String exchangeName = "traveldata_exchange";
    static final String exchangeName2 = "invoice_exchange";
    static final String routingKey = "traveldata_routingkey";
    static final String routingKey2 = "invoice_routingkey";

    @Bean
    Queue queue() {
        return new Queue(queueName, true);
    }

    @Bean
    Queue invoiceQueue() { return new Queue(queueName2, true); }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(exchangeName);
    }

    @Bean
    TopicExchange invoiceExchange() {
        return new TopicExchange(exchangeName2);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    @Bean
    Binding invoiceBinding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey2);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveTraveldata");
    }

}
