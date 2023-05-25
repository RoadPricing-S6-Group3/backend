package com.roadpricing.pricing.Model;

import java.math.BigDecimal;

public class Pricing {
    private int routeId;
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
