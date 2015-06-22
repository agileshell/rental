package com.insoul.rental.vo.request;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class UtilitiesPayment {

    private String firstRecord;

    private String lastRecord;

    private int quarter;

    private float price;

    private int totalPrice;

    private String comment;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date recordDate;

    public String getFirstRecord() {
        return firstRecord;
    }

    public void setFirstRecord(String firstRecord) {
        this.firstRecord = firstRecord;
    }

    public String getLastRecord() {
        return lastRecord;
    }

    public void setLastRecord(String lastRecord) {
        this.lastRecord = lastRecord;
    }

    public int getQuarter() {
        return quarter;
    }

    public void setQuarter(int quarter) {
        this.quarter = quarter;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

}
