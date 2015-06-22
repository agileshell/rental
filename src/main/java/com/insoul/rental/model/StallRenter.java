package com.insoul.rental.model;

import java.sql.Timestamp;
import java.util.Date;

public class StallRenter {

    private int stallRenterId;

    private int stallId;

    private int renterId;

    private boolean hasRented;

    private Date rentDate;

    private Date unrentDate;

    private String initWatermeter;

    private String initMeter;

    private String comment;

    private Timestamp created;

    private String renterName;

    private String stallName;

    public int getStallRenterId() {
        return stallRenterId;
    }

    public void setStallRenterId(int stallRenterId) {
        this.stallRenterId = stallRenterId;
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

    public boolean isHasRented() {
        return hasRented;
    }

    public void setHasRented(boolean hasRented) {
        this.hasRented = hasRented;
    }

    public Date getRentDate() {
        return rentDate;
    }

    public void setRentDate(Date rentDate) {
        this.rentDate = rentDate;
    }

    public Date getUnrentDate() {
        return unrentDate;
    }

    public void setUnrentDate(Date unrentDate) {
        this.unrentDate = unrentDate;
    }

    public String getInitWatermeter() {
        return initWatermeter;
    }

    public void setInitWatermeter(String initWatermeter) {
        this.initWatermeter = initWatermeter;
    }

    public String getInitMeter() {
        return initMeter;
    }

    public void setInitMeter(String initMeter) {
        this.initMeter = initMeter;
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

    public String getRenterName() {
        return renterName;
    }

    public void setRenterName(String renterName) {
        this.renterName = renterName;
    }

    public String getStallName() {
        return stallName;
    }

    public void setStallName(String stallName) {
        this.stallName = stallName;
    }

}
