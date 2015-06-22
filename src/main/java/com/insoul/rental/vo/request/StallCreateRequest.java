package com.insoul.rental.vo.request;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class StallCreateRequest {

    private int subareaId;

    private String name;

    private int monthPrice;

    private int renterId;

    private String comment;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date rentDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date unrentDate;

    private String initWatermeter;

    private String initMeter;

    private String rentComment;

    private RenterCreateRequest renter;

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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public String getRentComment() {
        return rentComment;
    }

    public void setRentComment(String rentComment) {
        this.rentComment = rentComment;
    }

    public RenterCreateRequest getRenter() {
        return renter;
    }

    public void setRenter(RenterCreateRequest renter) {
        this.renter = renter;
    }

}
