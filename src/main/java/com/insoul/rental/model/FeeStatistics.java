package com.insoul.rental.model;

import java.sql.Timestamp;

public class FeeStatistics {

    private String recordDate;
    private String year;
    private String month;
    private int quarter;

    private float stallRentFee;
    private float stallMeterFee;
    private float stallWaterFee;
    private float flatRentFee;
    private float flatMeterFee;
    private float totalFee;

    private Timestamp created;
    private Timestamp updated;

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getQuarter() {
        return quarter;
    }

    public void setQuarter(int quarter) {
        this.quarter = quarter;
    }

    public float getStallRentFee() {
        return stallRentFee;
    }

    public void setStallRentFee(float stallRentFee) {
        this.stallRentFee = stallRentFee;
    }

    public float getStallMeterFee() {
        return stallMeterFee;
    }

    public void setStallMeterFee(float stallMeterFee) {
        this.stallMeterFee = stallMeterFee;
    }

    public float getStallWaterFee() {
        return stallWaterFee;
    }

    public void setStallWaterFee(float stallWaterFee) {
        this.stallWaterFee = stallWaterFee;
    }

    public float getFlatRentFee() {
        return flatRentFee;
    }

    public void setFlatRentFee(float flatRentFee) {
        this.flatRentFee = flatRentFee;
    }

    public float getFlatMeterFee() {
        return flatMeterFee;
    }

    public void setFlatMeterFee(float flatMeterFee) {
        this.flatMeterFee = flatMeterFee;
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
