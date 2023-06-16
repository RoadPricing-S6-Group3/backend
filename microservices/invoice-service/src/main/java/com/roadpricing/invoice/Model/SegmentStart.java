package com.roadpricing.invoice.Model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public class SegmentStart implements Serializable {
    private String Id;
    private Double Lat;
    private Double Lon;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public Double getLat() {
        return Lat;
    }

    public void setLat(Double lat) {
        this.Lat = lat;
    }

    public Double getLon() {
        return Lon;
    }

    public void setLon(Double lon) {
        this.Lon = lon;
    }
}
