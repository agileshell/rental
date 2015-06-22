package com.insoul.rental.model;

import java.sql.Timestamp;
import java.util.Date;

public class StallPaymentHistory {

    private int id;

    private int stallRenterId;

    private Date startDate;

    private Date endDate;

    private int quarter;

    private int totalPrice;

    private String comment;

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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getQuarter() {
        return quarter;
    }

    public void setQuarter(int quarter) {
        this.quarter = quarter;
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
