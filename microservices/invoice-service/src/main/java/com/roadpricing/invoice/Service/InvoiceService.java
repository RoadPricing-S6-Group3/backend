package com.roadpricing.invoice.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roadpricing.invoice.Dto.PriceDto;
import com.roadpricing.invoice.Model.*;
import com.roadpricing.invoice.Repo.IncomingInvoiceRepo;
import com.roadpricing.invoice.Repo.InvoiceRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    @Autowired
    InvoiceRepo repo;

    @Autowired
    IncomingInvoiceRepo incomingInvoiceRepo;
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PriceService priceService;

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
    public void sendInvoiceToCountry(List<PriceDto> outgoing, String cc){
        String countryToSendTo = cc;
        IncomingInvoice invoice = createInvoiceFromPriceDTO(outgoing);
        if(invoice != null && countryToSendTo.isBlank() != true && countryToSendTo.isEmpty() != true && countryToSendTo != null){
            sendInvoice(invoice, countryToSendTo);
        }
    }

    private void sendInvoice(IncomingInvoice invoice, String cc){
        RestTemplate restTemplate = new RestTemplate();
        String url = createUrl(cc);
        try{
            logger.info("Try Sending to " + cc);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<IncomingInvoice> invoiceHttpEntity = new HttpEntity<>(invoice, headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, invoiceHttpEntity, String.class);
            logger.info(response.toString());
            if(response.getStatusCode().is2xxSuccessful()){
                IncomingInvoiceMongo found = incomingInvoiceRepo.findByRouteDBId(invoice.getId()).orElse(null);
                if(found != null){
                    found.setPaymentStatus("RECEIVED");
                    incomingInvoiceRepo.save(found);
                }
                logger.info("Successfully sent invoice to: " + cc.toUpperCase());
            }
        }
        catch (Exception e){
            logger.info("Could not send Request");
            IncomingInvoiceMongo found = incomingInvoiceRepo.findByRouteDBId(invoice.getId()).orElse(null);
            if(found != null){
                found.setPaymentStatus("FAILED");
                incomingInvoiceRepo.save(found);
            }
        }
    }
    private String createUrl(String cc){
        String url = "N/A";
        if(cc.equals("nl") || cc.equals("NL")){
            url = "http://34.140.232.108/api/invoice/return-processed?cc=" + cc.toUpperCase();
        }
        else if(cc.equals("be") || cc.equals("BE")){
            url = "https://international.oibss.nl/return-processed?cc="+cc.toUpperCase();
        }
        else if (cc.equals("lux") || cc.equals("LUX") || cc.equals("lu") || cc.equals("LU")){
            url = "http://34.159.70.126/api/return-processed?cc="+cc.toUpperCase();
        }
        return url;
    }
    private IncomingInvoice createInvoiceFromPriceDTO(List<PriceDto> dtos){
        IncomingInvoice invoice = new IncomingInvoice();
        invoice.setId(dtos.get(0).getRouteId());
        double priceTotal = 0;
        List<InvoiceSegment> segments = new ArrayList<>();
        for (PriceDto priceDTO: dtos)
        {
            InvoiceSegment segment = priceDTO.getSegment();
            priceTotal = priceTotal + segment.getPrice();
            segments.add(segment);
        }
        invoice.setPriceTotal(priceTotal);
        invoice.setSegments(segments);
        logger.info("Created an Invoice");
        IncomingInvoiceMongo invoiceToSave = new IncomingInvoiceMongo();
        invoiceToSave.setRouteDBId(invoice.getId());
        invoiceToSave.setPaymentStatus("PENDING");
        invoiceToSave.setIncomingInvoice(invoice);
        incomingInvoiceRepo.save(invoiceToSave);
        priceService.deleteAllByRoutId(invoice.getId());
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
