package com.insoul.rental.vo.request;

public class FlatListRequest extends PaginationRequest {

    private String name;

    private Boolean isRented;

    private Integer renterId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsRented() {
        return isRented;
    }

    public void setIsRented(Boolean isRented) {
        this.isRented = isRented;
    }

    public Integer getRenterId() {
        return renterId;
    }

    public void setRenterId(Integer renterId) {
        this.renterId = renterId;
    }

}
