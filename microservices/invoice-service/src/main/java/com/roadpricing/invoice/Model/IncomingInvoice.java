package com.roadpricing.invoice.Model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "invoices")
public class IncomingInvoice implements Serializable {
    private String Id;

    private Double PriceTotal;

    private List<InvoiceSegment> Segments;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public Double getPriceTotal() {
        return PriceTotal;
    }

    public void setPriceTotal(Double priceTotal) {
        this.PriceTotal = priceTotal;
    }

    public List<InvoiceSegment> getSegments() {
        return Segments;
    }

    public void setSegments(List<InvoiceSegment> segments) {
        this.Segments = segments;
    }
}
