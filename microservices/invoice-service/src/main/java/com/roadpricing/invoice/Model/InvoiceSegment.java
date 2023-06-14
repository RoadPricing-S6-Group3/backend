package com.roadpricing.invoice.Model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
public class InvoiceSegment implements Serializable {

    private Date time;
    private Double price;

    private SegmentStart start;

    private SegmentWay way;

    private SegmentEnd end;

//Getters & Setters

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public SegmentStart getStart() {
        return start;
    }

    public void setStart(SegmentStart start) {
        this.start = start;
    }

    public SegmentWay getWay() {
        return way;
    }

    public void setWay(SegmentWay way) {
        this.way = way;
    }

    public SegmentEnd getEnd() {
        return end;
    }

    public void setEnd(SegmentEnd end) {
        this.end = end;
    }
}
