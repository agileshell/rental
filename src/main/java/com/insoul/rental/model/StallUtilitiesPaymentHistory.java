package com.insoul.rental.model;

import java.sql.Timestamp;
import java.util.Date;

public class StallUtilitiesPaymentHistory {

    private int id;

    private int stallRenterId;

    private String firstRecord;

    private String lastRecord;

    private int type;

    private int quarter;

    private float price;

    private int totalPrice;

    private String comment;

    private Date recordDate;

    private Timestamp created;

    private int stallId;

    private int renterId;

    private String renterName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStallRenterId() {
        return stallRenterId;
    }

    public void setStallRenterId(int stallRenterId) {
        this.stallRenterId = stallRenterId;
    }

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public int getStallId() {
        return stallId;
    }

    public void setStallId(int stallId) {
        this.stallId = stallId;
    }

    public int getRenterId() {
        return renterId;
    }

    public void setRenterId(int renterId) {
        this.renterId = renterId;
    }

    public String getRenterName() {
        return renterName;
    }

    public void setRenterName(String renterName) {
        this.renterName = renterName;
    }

}
