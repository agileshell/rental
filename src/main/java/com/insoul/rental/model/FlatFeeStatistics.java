package com.insoul.rental.model;

import java.sql.Timestamp;

public class FlatFeeStatistics {

    private int id;
    private int flatRenterId;
    private String year;
    private int quarter;

    private int isPayRent;
    private int isPayMeter;
    private int isPayAll;

    private float rentFee;
    private float meterFee;
    private float totalFee;

    private Timestamp created;
    private Timestamp updated;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFlatRenterId() {
        return flatRenterId;
    }

    public void setFlatRenterId(int flatRenterId) {
        this.flatRenterId = flatRenterId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getQuarter() {
        return quarter;
    }

    public void setQuarter(int quarter) {
        this.quarter = quarter;
    }

    public int getIsPayRent() {
        return isPayRent;
    }

    public void setIsPayRent(int isPayRent) {
        this.isPayRent = isPayRent;
    }

    public int getIsPayMeter() {
        return isPayMeter;
    }

    public void setIsPayMeter(int isPayMeter) {
        this.isPayMeter = isPayMeter;
    }

    public int getIsPayAll() {
        return isPayAll;
    }

    public void setIsPayAll(int isPayAll) {
        this.isPayAll = isPayAll;
    }

    public float getRentFee() {
        return rentFee;
    }

    public void setRentFee(float rentFee) {
        this.rentFee = rentFee;
    }

    public float getMeterFee() {
        return meterFee;
    }

    public void setMeterFee(float meterFee) {
        this.meterFee = meterFee;
    }

    public float getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(float totalFee) {
        this.totalFee = totalFee;
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

}
