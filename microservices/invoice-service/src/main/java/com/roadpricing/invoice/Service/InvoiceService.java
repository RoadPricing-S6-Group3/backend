package com.roadpricing.invoice.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roadpricing.invoice.Dto.PriceDto;
import com.roadpricing.invoice.Model.IncomingInvoice;
import com.roadpricing.invoice.Model.Invoice;
import com.roadpricing.invoice.Model.PaymentStatus;
import com.roadpricing.invoice.Repo.InvoiceRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.DataInput;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    @Autowired
    InvoiceRepo repo;

    @Autowired
    ObjectMapper objectMapper;

    Logger logger = LoggerFactory.getLogger(InvoiceService.class);

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
    public void sendInvoiceToCountry(PriceDto outgoing){
        String countryToSendTo = outgoing.getCountryCode();
        IncomingInvoice invoice = createInvoiceFromPriceDTO(outgoing);
        if(invoice != null && countryToSendTo.isBlank() != true && countryToSendTo.isEmpty() != true && countryToSendTo != null){
            sendInvoice(invoice, countryToSendTo);
        }
    }

    private void sendInvoice(IncomingInvoice invoice, String cc){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response;
        if(cc == "LUX" || cc == "lux"){
            String url = "http://34.159.70.126/api/return-processed";
            try{
                response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
                logger.info(response.getStatusCode().toString());
            }
            catch (Exception e){
                logger.info("Could not send Request");
            }
        } else if (cc == "BE" || cc == "be") {
            try{
                String url = "https://international.oibss.nl/api/return-processed";
                response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
                logger.info(response.getStatusCode().toString());
            }
            catch (Exception e){
                logger.info("Could not send Request");
            }
        }
    }
    private IncomingInvoice createInvoiceFromPriceDTO(PriceDto dto){
        IncomingInvoice invoice = new IncomingInvoice();
        invoice.setId(dto.getRouteId());
        try{
            logger.info("Processed");

        }
        catch (Exception e){
            logger.error("ERROR: " + e);
        }
        return invoice;
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
