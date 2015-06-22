package com.insoul.rental.vo;

import java.sql.Timestamp;

public class StallListVO {

    private int stallId;

    private String subarea;

    private String name;

    private int monthPrice;

    private int renterId;

    private Timestamp created;

    private String renterName;

    private boolean hasPaidRent;

    private boolean hasPaidWatermeter;

    private boolean hasPaidMeter;

    public int getStallId() {
        return stallId;
    }

    public void setStallId(int stallId) {
        this.stallId = stallId;
    }

    public String getSubarea() {
        return subarea;
    }

    public void setSubarea(String subarea) {
        this.subarea = subarea;
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

    public boolean isHasPaidRent() {
        return hasPaidRent;
    }

    public void setHasPaidRent(boolean hasPaidRent) {
        this.hasPaidRent = hasPaidRent;
    }

    public boolean isHasPaidWatermeter() {
        return hasPaidWatermeter;
    }

    public void setHasPaidWatermeter(boolean hasPaidWatermeter) {
        this.hasPaidWatermeter = hasPaidWatermeter;
    }

    public boolean isHasPaidMeter() {
        return hasPaidMeter;
    }

    public void setHasPaidMeter(boolean hasPaidMeter) {
        this.hasPaidMeter = hasPaidMeter;
    }

}
