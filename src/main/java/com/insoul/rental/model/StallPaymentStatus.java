package com.insoul.rental.model;

public class StallPaymentStatus {

    private int stallId;

    private boolean hasPaidRent;

    public int getStallId() {
        return stallId;
    }

    public void setStallId(int stallId) {
        this.stallId = stallId;
    }

    public boolean isHasPaidRent() {
        return hasPaidRent;
    }

    public void setHasPaidRent(boolean hasPaidRent) {
        this.hasPaidRent = hasPaidRent;
    }

}
