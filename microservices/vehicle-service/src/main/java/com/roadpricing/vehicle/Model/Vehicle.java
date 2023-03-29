package com.roadpricing.vehicle.Model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="vehicles")
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {
    @Id
    private Long id;

    @Column(name="vehicleModel")
    private String vehicleModel;

    @Column(name="license")
    private String license;

    @Column(name="efficiencyLabel")
    private String efficiencyLabel;

    @Column(name="fuelType")
    private String fuelType;

    @Column(name="ownderId")
    private Long ownerId;

    //Getters & Setters

    public Long getId() {
        return id;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public String getLicense() {
        return license;
    }

    public String getEfficiencyLabel() {
        return efficiencyLabel;
    }

    public String getFuelType() {
        return fuelType;
    }

    public Long getOwnerId() {
        return ownerId;
    }
}
