package com.insoul.rental.criteria;

public class StallStatisticCriteria extends PaginationCriteria {

    private String stallName;

    private String rentor;

    private String year;

    private Integer quarter;

    private String type = "all";

    private boolean isPaid;

    public String getStallName() {
        return stallName;
    }

    public void setStallName(String stallName) {
        this.stallName = stallName;
    }

    public String getRentor() {
        return rentor;
    }

    public void setRentor(String rentor) {
        this.rentor = rentor;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Integer getQuarter() {
        return quarter;
    }

    public void setQuarter(Integer quarter) {
        this.quarter = quarter;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

}
