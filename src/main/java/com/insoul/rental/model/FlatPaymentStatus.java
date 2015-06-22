package com.insoul.rental.model;

public class FlatPaymentStatus {

    private int flatId;

    private boolean hasPaidRent;

    public int getFlatId() {
        return flatId;
    }

    public void setFlatId(int flatId) {
        this.flatId = flatId;
    }

    public boolean isHasPaidRent() {
        return hasPaidRent;
    }

    public void setHasPaidRent(boolean hasPaidRent) {
        this.hasPaidRent = hasPaidRent;
    }

}
