package com.roadpricing.invoice.Model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "IncomingInvoices")
@NoArgsConstructor
@AllArgsConstructor
public class IncomingInvoiceMongo{


    @Id
    @MongoId
    private String mongoId;

    private String routeDBId;

    private String paymentStatus;

    private IncomingInvoice incomingInvoice;

    public String getMongoId() {
        return mongoId;
    }

    public void setMongoId(String mongoId) {
        this.mongoId = mongoId;
    }

    public String getRouteDBId() {
        return routeDBId;
    }

    public void setRouteDBId(String routeDBId) {
        this.routeDBId = routeDBId;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public IncomingInvoice getIncomingInvoice() {
        return incomingInvoice;
    }

    public void setIncomingInvoice(IncomingInvoice incomingInvoice) {
        this.incomingInvoice = incomingInvoice;
    }
}
