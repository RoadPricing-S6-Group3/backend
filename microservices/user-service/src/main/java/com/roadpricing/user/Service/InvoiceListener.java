package com.roadpricing.user.Service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class InvoiceListener {

    private final Logger log;
    public InvoiceListener() {
        this.log = LoggerFactory.getLogger(InvoiceListener.class);
    }
    @RabbitListener(queues = "invoice_queue")
    public void listen (String in){
        log.info("Message recieved from Queue: " + in);
    }
}
