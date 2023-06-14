package com.roadpricing.pricing.Dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

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
