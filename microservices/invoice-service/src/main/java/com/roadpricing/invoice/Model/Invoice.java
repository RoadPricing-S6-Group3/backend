package com.roadpricing.invoice.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "userId")
    Long userId;

    @Column(name = "routeId")
    Long routeId;

    @Column(name = "amountToPay")
    Long amountToPay;


    //Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    public Long getAmountToPay() {
        return amountToPay;
    }

    public void setAmountToPay(Long amountToPay) {
        this.amountToPay = amountToPay;
    }
}
