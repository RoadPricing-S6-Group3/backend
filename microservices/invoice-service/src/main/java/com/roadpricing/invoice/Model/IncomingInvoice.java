package com.roadpricing.invoice.Model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class IncomingInvoice implements Serializable {
        private String id;

    private Double priceTotal;

    private List<InvoiceSegment> segments;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(Double priceTotal) {
        this.priceTotal = priceTotal;
    }

    public List<InvoiceSegment> getSegments() {
        return segments;
    }

    public void setSegments(List<InvoiceSegment> segments) {
        this.segments = segments;
    }
}
