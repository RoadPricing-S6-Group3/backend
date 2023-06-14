package com.roadpricing.pricing.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;

@Document(collection="pricing")
public class Pricing implements Serializable {
    @Id
    private String routeId;
    private boolean inProgress;
    private String vehicleType;
    private String fuelType;
    private String roadType;
    private String roadName;
    private BigDecimal distance;
    private String countryCode;
    private String time;
    private Double startLat;
    private Double startLon;
    private Double endLat;
    private Double endLon;

    public String getRouteId(){
        return routeId;
    }

    public void setRouteId(String routeId){
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

    public boolean isInProgress() {
        return inProgress;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getTime() { return time; }

    public void setTime(String time) { this.time = time; }

    public Double getStartLat() { return startLat; }

    public void setStartLat(Double startLat) { this.startLat = startLat; }

    public Double getStartLon() { return startLon; }

    public void setStartLon(Double startLon) { this.startLon = startLon; }

    public Double getEndLat() { return endLat; }

    public void setEndLat(Double endLat) { this.endLat = endLat; }

    public Double getEndLon() { return endLon; }

    public void setEndLon(Double endLon) { this.endLon = endLon; }
}
