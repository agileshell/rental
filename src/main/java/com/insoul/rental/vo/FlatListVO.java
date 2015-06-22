package com.insoul.rental.vo;

import java.sql.Timestamp;

public class FlatListVO {

    private int flatId;

    private String name;

    private int monthPrice;

    private int renterId;

    private Timestamp created;

    private String renterName;

    private boolean hasPaidRent;

    private boolean hasPaidMeter;

    public int getFlatId() {
        return flatId;
    }

    public void setFlatId(int flatId) {
        this.flatId = flatId;
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

    public boolean isHasPaidMeter() {
        return hasPaidMeter;
    }

    public void setHasPaidMeter(boolean hasPaidMeter) {
        this.hasPaidMeter = hasPaidMeter;
    }

}
