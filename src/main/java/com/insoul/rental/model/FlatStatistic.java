package com.insoul.rental.model;

public class FlatStatistic {

    private String year;
    private int quarter;

    private int flatId;
    private String flatName;
    private int renterId;
    private String renterName;

    private int isPayRent;
    private int isPayMeter;
    private int isPayAll;

    private float rentFee;
    private float meterFee;
    private float totalFee;

    private int statisticId;

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

    public int getFlatId() {
        return flatId;
    }

    public void setFlatId(int flatId) {
        this.flatId = flatId;
    }

    public String getFlatName() {
        return flatName;
    }

    public void setFlatName(String flatName) {
        this.flatName = flatName;
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

    public int getStatisticId() {
        return statisticId;
    }

    public void setStatisticId(int statisticId) {
        this.statisticId = statisticId;
    }

}
