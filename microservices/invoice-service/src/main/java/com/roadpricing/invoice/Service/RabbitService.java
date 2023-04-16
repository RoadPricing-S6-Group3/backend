package com.roadpricing.invoice.Service;

import com.roadpricing.invoice.Model.Invoice;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitService {
    @Autowired
    RabbitTemplate rabbitTemplate;

    public void sendInvoiceToQueue(Invoice invoice){
        rabbitTemplate.convertAndSend(invoice);
    }
}
