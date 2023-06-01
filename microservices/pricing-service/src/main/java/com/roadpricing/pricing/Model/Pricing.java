package com.roadpricing.pricing.Model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;

@Document(collection="pricing")
public class Pricing implements Serializable {
    private int routeId;
    private boolean inProgress;
    private String vehicleType;
    private String fuelType;
    private String roadType;
    private String roadName;
    private BigDecimal distance;

    public int getRouteId(){
        return routeId;
    }

    public void setRouteId(int routeId){
        this.routeId = routeId;
    }

    public boolean getInProgress() { return inProgress; }

    public void setInProgress(boolean inProgress) { this.inProgress = inProgress; }

    public String getVehicleType(){
        return vehicleType;
    }

    public void setVehicleType(String vehicleType){
        this.vehicleType = vehicleType;
    }

    public String getFuelType(){
        return fuelType;
    }

    public void setFuelType(String fuelType){
        this.fuelType = fuelType;
    }

    public String getRoadType(){
        return roadType;
    }

    public void setRoadType(String roadType){
        this.roadType = roadType;
    }

    public String getRoadName(){
        return roadName;
    }

    public void setRoadName(String roadName){
        this.roadName = roadName;
    }

    public BigDecimal getDistance(){
        return distance;
    }

    public void setDistance(BigDecimal distance){
        this.distance = distance;
    }
}
