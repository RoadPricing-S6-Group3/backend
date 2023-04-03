package com.roadpricing.invoice.Service;

import com.roadpricing.invoice.Model.Invoice;
import com.roadpricing.invoice.Repo.InvoiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    @Autowired
    InvoiceRepo repo;

    public Optional<Invoice> findById(Long id){
        Invoice invoice = repo.findById(id).orElse(null);
        return Optional.ofNullable(invoice);
    }
    public List<Invoice> findAll(){
        List<Invoice> invoiceList = repo.findAll();
        return invoiceList;
    }
    public void saveInvoice(Invoice invoice){
        repo.save(invoice);
    }
    public Invoice updateInvoice(Invoice invoice){
        Invoice existingInvoice = repo.findById(invoice.getId()).get();

        existingInvoice.setAmountToPay(invoice.getAmountToPay());

        return repo.save(invoice);
    }
    public void deleteInvoice(Long invoiceId){
        repo.deleteById(invoiceId);
    }
}
