package com.insoul.rental.criteria;

public class StallCriteria extends PaginationCriteria {

    private Integer subareaId;

    private String name;

    private Boolean isRented;

    private Integer renterId;

    public Integer getSubareaId() {
        return subareaId;
    }

    public void setSubareaId(Integer subareaId) {
        this.subareaId = subareaId;
    }

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
