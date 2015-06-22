package com.insoul.rental.model;

import java.sql.Timestamp;

public class Stall {

    private int stallId;

    private int subareaId;

    private String name;

    private int monthPrice;

    private int renterId;

    private int stallRenterId;

    private String comment;

    private Timestamp created;

    private Timestamp updated;

    private String renterName;

    public int getStallId() {
        return stallId;
    }

    public void setStallId(int stallId) {
        this.stallId = stallId;
    }

    public int getSubareaId() {
        return subareaId;
    }

    public void setSubareaId(int subareaId) {
        this.subareaId = subareaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMonthPrice() {
        return monthPrice;
    }

    public void setMonthPrice(int monthPrice) {
        this.monthPrice = monthPrice;
    }

    public int getRenterId() {
        return renterId;
    }

    public void setRenterId(int renterId) {
        this.renterId = renterId;
    }

    public int getStallRenterId() {
        return stallRenterId;
    }

    public void setStallRenterId(int stallRenterId) {
        this.stallRenterId = stallRenterId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    public String getRenterName() {
        return renterName;
    }

    public void setRenterName(String renterName) {
        this.renterName = renterName;
    }

}
