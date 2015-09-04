package com.insoul.rental.vo;

import java.io.Serializable;

public class SubareaVO implements Serializable {

    private static final long serialVersionUID = 3067784154503144914L;

    private int subareaId;

    private String name;

    private String comment;

    public int getSubareaId() {
        return subareaId;
    }

    public void setSubareaId(int subareaId) {
        this.subareaId = subareaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
