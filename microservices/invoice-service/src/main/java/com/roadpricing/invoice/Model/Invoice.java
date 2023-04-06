package com.roadpricing.invoice.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

import static org.hibernate.type.EnumType.ENUM;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invoices")
public class Invoice implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "userId")
    Long userId;

    @Column(name = "routeId")
    Long routeId;

    @Column(name = "amountToPay")
    Long amountToPay;

    @Column(name = "paymentStatus", columnDefinition = "enum('PAID', 'UNPAID', 'PENDING') default 'unpaid'")
    private PaymentStatus status;

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
