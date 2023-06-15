package com.roadpricing.invoice.Dto;

import com.roadpricing.invoice.Model.InvoiceSegment;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public class PriceDto implements Serializable {
    private String routeId;
    private String countryCode;
    private InvoiceSegment segment;



    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getCountryCode(){
        return countryCode;
    }

    public void setCountryCode(String countryCode){
        this.countryCode = countryCode;
    }

    public InvoiceSegment getSegment() {
        return this.segment;
    }

    public void setSegment(InvoiceSegment segment) {
        this.segment = segment;
    }
}
