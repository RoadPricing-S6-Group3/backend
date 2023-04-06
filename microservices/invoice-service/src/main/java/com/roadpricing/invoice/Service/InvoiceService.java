package com.roadpricing.invoice.Service;

import com.roadpricing.invoice.Model.Invoice;
import com.roadpricing.invoice.Model.PaymentStatus;
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
        if(invoice.getPaymentStatus() == null){
            invoice.setPaymentStatus(PaymentStatus.UNPAID);
            repo.save(invoice);
        }
        else{
            repo.save(invoice);
        }
    }

    public Invoice updateInvoice(Invoice newInvoice){
        Invoice oldInvoice = repo.findById(newInvoice.getId()).orElse(null);
        if(oldInvoice != null){
//            Invoice oldInvoice = repo.findById(newInvoice.getId()).get();
//
//            oldInvoice.setAmountToPay(newInvoice.getAmountToPay());
//            oldInvoice.setRouteId(newInvoice.getRouteId());
//            oldInvoice.setUserId(newInvoice.getUserId());
            return repo.save(newInvoice);
        }
        return null;
    }

    public void deleteInvoice(Long invoiceId){
        repo.deleteById(invoiceId);
    }
}
