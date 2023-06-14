package com.roadpricing.invoice.Controller;

import com.roadpricing.invoice.Model.IncomingInvoice;
import com.roadpricing.invoice.Model.Invoice;
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
    @PostMapping("/return-processed")
    public ResponseEntity receiveInvoice(@RequestBody IncomingInvoice incomingInvoice){
        try{
            logger.info("Received an Incoming Invoice");
            logger.info("Invoice: " + "[ " + incomingInvoice.getId() + " ]" + incomingInvoice.getSegments());
            return ResponseEntity.ok().build();
        }
        catch (Exception e){
            logger.error("Error: " + e);
            return ResponseEntity.badRequest().build();
        }
    }
}
