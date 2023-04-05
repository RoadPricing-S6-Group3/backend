package com.roadpricing.invoice.Controller;

import com.roadpricing.invoice.Model.Invoice;
import com.roadpricing.invoice.Service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/invoice")
public class InvoiceController {

    @Autowired
    InvoiceService service;

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
}
