package com.insoul.rental.vo;

public class StallDetailVO {

    private int stallId;

    private SubareaVO subarea;

    private String name;

    private int monthPrice;

    private int renterId;

    private String comment;

    private StallRenterDetailVO stallRenter;

    public int getStallId() {
        return stallId;
    }

    public void setStallId(int stallId) {
        this.stallId = stallId;
    }

    public SubareaVO getSubarea() {
        return subarea;
    }

    public void setSubarea(SubareaVO subarea) {
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public StallRenterDetailVO getStallRenter() {
        return stallRenter;
    }

    public void setStallRenter(StallRenterDetailVO stallRenter) {
        this.stallRenter = stallRenter;
    }

}
