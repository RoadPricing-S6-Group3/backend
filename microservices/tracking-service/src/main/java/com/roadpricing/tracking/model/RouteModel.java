package com.roadpricing.tracking.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
public class RouteModel implements Serializable {

    private long routeId;
    private Map <Integer, String> coords;
    private BigDecimal distance;
    private  BigDecimal duration;

    public Map<Integer, String> getCoords() {
        return coords;
    }

    public void setCoords(Map<Integer, String> coords) {
        this.coords = coords;
    }

    public long getRouteId() {
        return routeId;
    }

    public void setRouteId(long routeId) {
        this.routeId = routeId;
    }
    public BigDecimal getDistance() {
        return distance;
    }

    public void setDistance(BigDecimal distance) {
        this.distance = distance;
    }

    public BigDecimal getDuration() {
        return duration;
    }

    public void setDuration(BigDecimal duration) {
        this.duration = duration;
    }
}
