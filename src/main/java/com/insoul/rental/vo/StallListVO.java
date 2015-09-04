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

    private int isPayRent;

    private int isPayMeter;

    private int isPayWater;

    private int statisticId;

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

    public int getStatisticId() {
        return statisticId;
    }

    public void setStatisticId(int statisticId) {
        this.statisticId = statisticId;
    }

}
