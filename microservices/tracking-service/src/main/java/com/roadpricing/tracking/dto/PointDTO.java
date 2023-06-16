package com.roadpricing.tracking.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
public class PointDTO implements Serializable, Comparable<PointDTO> {
    private String lat;
    private String lon;
    private Date time;

    //Getters & Setters
    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public int compareTo(PointDTO toCompare) {
        return getTime().compareTo(toCompare.getTime());
    }
}
