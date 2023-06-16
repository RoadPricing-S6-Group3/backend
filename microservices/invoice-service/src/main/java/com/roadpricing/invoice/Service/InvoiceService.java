package com.roadpricing.invoice.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roadpricing.invoice.Dto.PriceDto;
import com.roadpricing.invoice.Model.*;
import com.roadpricing.invoice.Repo.IncomingInvoiceRepo;
import com.roadpricing.invoice.Repo.InvoiceRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.DataInput;
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
        ResponseEntity<String> response = null;
        String url = createUrl(cc);
        try{
            logger.info("Try Sending to " + cc);
            response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            logger.info(response.toString());
        }
        catch (Exception e){
            logger.info("Could not send Request");
        }
        priceService.deleteAllByRoutId(invoice.getId());
    }
    private String createUrl(String cc){
        String url = "N/A";
        if(cc.equals("nl") || cc.equals("NL")){
            url = "http://34.140.232.108/api/return-processed";
        }
        else if(cc.equals("be") || cc.equals("BE")){
            url = "https://international.oibss.nl/api/return-processed";
        }
        else if (cc.equals("lux") || cc.equals("LUX")){
            url = "http://34.159.70.126/api/return-processed";
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
