package com.insoul.rental.vo;

import java.util.Date;

public class FlatRenterDetailVO {

    private int flatRenterId;

    private int renterId;

    private boolean hasRented;

    private Date rentDate;

    private Date unrentDate;

    private String initMeter;

    private String comment;

    private String renterName;

    public int getFlatRenterId() {
        return flatRenterId;
    }

    public void setFlatRenterId(int flatRenterId) {
        this.flatRenterId = flatRenterId;
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

    public String getRenterName() {
        return renterName;
    }

    public void setRenterName(String renterName) {
        this.renterName = renterName;
    }

}
