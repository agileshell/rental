package com.insoul.rental.model;

import java.sql.Timestamp;

public class StallFeeStatistics {

    private int id;
    private int stallRenterId;
    private String year;
    private int quarter;

    private int isPayRent;
    private int isPayMeter;
    private int isPayWater;
    private int isPayAll;

    private float rentFee;
    private float meterFee;
    private float waterFee;
    private float totalFee;

    private Timestamp created;
    private Timestamp updated;

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

    public int getIsPayWater() {
        return isPayWater;
    }

    public void setIsPayWater(int isPayWater) {
        this.isPayWater = isPayWater;
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

    public float getWaterFee() {
        return waterFee;
    }

    public void setWaterFee(float waterFee) {
        this.waterFee = waterFee;
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
