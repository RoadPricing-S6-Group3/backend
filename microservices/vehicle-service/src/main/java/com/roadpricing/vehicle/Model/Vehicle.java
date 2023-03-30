package com.roadpricing.vehicle.Model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="vehicles")
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public void setId(Long id) {
        this.id = id;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getEfficiencyLabel() {
        return efficiencyLabel;
    }

    public void setEfficiencyLabel(String efficiencyLabel) {
        this.efficiencyLabel = efficiencyLabel;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}
