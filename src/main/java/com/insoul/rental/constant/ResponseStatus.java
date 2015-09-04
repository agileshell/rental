package com.insoul.rental.constant;

public enum ResponseStatus {

    SUCCEED("succeed"), FAILED("failed");

    private String value;

    private ResponseStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
