package com.roadpricing.invoice.Model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public class InvoiceSegment implements Serializable {

    private String Time;
    private Double Price;

    private SegmentStart Start;

    private SegmentWay Way;

    private SegmentEnd End;

//Getters & Setters

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        this.Time = time;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        this.Price = price;
    }

    public SegmentStart getStart() {
        return Start;
    }

    public void setStart(SegmentStart start) {
        this.Start = start;
    }

    public SegmentWay getWay() {
        return Way;
    }

    public void setWay(SegmentWay way) {
        this.Way = way;
    }

    public SegmentEnd getEnd() {
        return End;
    }

    public void setEnd(SegmentEnd end) {
        this.End = end;
    }
}
