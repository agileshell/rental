package com.insoul.rental.vo;

public class FlatDetailVO {

    private int flatId;

    private String name;

    private int monthPrice;

    private int renterId;

    private String comment;

    private FlatRenterDetailVO flatRenter;

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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public FlatRenterDetailVO getFlatRenter() {
        return flatRenter;
    }

    public void setFlatRenter(FlatRenterDetailVO flatRenter) {
        this.flatRenter = flatRenter;
    }

}
