package com.insoul.rental.vo.request;

public class PaginationRequest {

    private int curn = 1;

    private int ps = 20;

    public int getCurn() {
        return curn;
    }

    public void setCurn(int curn) {
        this.curn = curn;
    }

    public int getPs() {
        return ps;
    }

    public void setPs(int ps) {
        this.ps = ps;
    }

}
