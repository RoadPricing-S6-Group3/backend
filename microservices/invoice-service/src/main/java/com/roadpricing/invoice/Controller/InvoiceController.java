package com.roadpricing.invoice.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roadpricing.invoice.Model.IncomingInvoice;
import com.roadpricing.invoice.Model.Invoice;
import com.roadpricing.invoice.Model.InvoiceSegment;
import com.roadpricing.invoice.Service.InvoiceService;
import com.roadpricing.invoice.Service.RabbitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/invoice")
public class InvoiceController {
    @Autowired
    InvoiceService service;
    @Autowired
    RabbitService rabbitService;

    @Autowired
    ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(InvoiceController.class);

    @GetMapping()
    public ResponseEntity<List<Invoice>> getAllInvoices(){
        try {
            List<Invoice> invoices = service.findAll();
            return ResponseEntity.ok().body(invoices);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable("id") Long invoiceId){
        try{
            Invoice invoice = service.findById(invoiceId).get();
            return ResponseEntity.ok().body(invoice);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping()
    public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoice){
        try {
            service.saveInvoice(invoice);
            rabbitService.sendInvoiceToQueue(invoice);
            return ResponseEntity.ok().body(invoice);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Invoice> updateInvoice(@PathVariable("id") Long invoiceId,
                                                 @RequestBody Invoice invoiceToUpdate){
        try{
            Invoice updatedInvoice = service.updateInvoice(invoiceToUpdate);
            return ResponseEntity.ok().body(updatedInvoice);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteInvoice(@PathVariable("id") Long invoiceId){
        try{
            service.deleteInvoice(invoiceId);
            return ResponseEntity.ok().body("Removed invoice");
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/return-processed/{cc}")
    public ResponseEntity receiveInvoice(@RequestBody String incomingInvoice, @PathVariable(value = "cc")String cc){
        String ccEmoji = "";
        if(cc.equals("nl") || cc.equals("NL")){
            ccEmoji = " 🧀 🇳🇱 ";
        }
        else if(cc.equals("be") || cc.equals("BE")){
            ccEmoji = " 🧇 🇧🇪";
        }
        else if (cc.equals("lux") || cc.equals("LUX")){
            ccEmoji = " 💰✨ 🇱🇺";
        }
        logger.info("Received an Incoming Invoice 📄 from country: [ " + cc + ccEmoji +" ]");
        logger.info(incomingInvoice);
        try{
            IncomingInvoice invoice =  objectMapper.readValue(incomingInvoice, IncomingInvoice.class);
            logger.info("Invoice id: " + "[ " + invoice.getId() + " ]" + "[ 🪪 ]");
            logger.info("Invoice price: " + "[ " + invoice.getPriceTotal() + " ]" + "[ 💰 ]");
            if(invoice.getSegments() != null){
                for (InvoiceSegment segment: invoice.getSegments()) {
                    logger.info("Segment time: " + "[ " + segment.getTime() + " ]" + "[ 🕜 ]");
                    logger.info("Segment way: " + "[ " + segment.getWay().getId() + " ]" + "[ 🛣️ ]");
                    logger.info("Segment Price: " + "[ " + segment.getPrice() + " ]" + "[ 🪙 ]");
                }
            }
            return ResponseEntity.ok().build();
        }
        catch (Exception e){
            logger.error("Error: " + e);
            logger.error("Invoice is not correct: ❌");
            return ResponseEntity.badRequest().build();
        }
    }
}
