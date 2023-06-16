package com.roadpricing.invoice.Dto;

import com.roadpricing.invoice.Model.InvoiceSegment;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "priceDTO")
public class PriceDto implements Serializable {

    @Id
    @MongoId
    private String id;

    private Boolean inProgress;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getInProgress() {
        return inProgress;
    }

    public void setInProgress(Boolean inProgress) {
        this.inProgress = inProgress;
    }
}
