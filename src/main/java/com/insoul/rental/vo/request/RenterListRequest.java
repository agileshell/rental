package com.insoul.rental.vo.request;

public class RenterListRequest extends PaginationRequest {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
