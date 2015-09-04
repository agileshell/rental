package com.insoul.rental.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class SystemSetting implements Serializable {

    private static final long serialVersionUID = -7530468863099791816L;

    private int systemSettingId;

    private String key;

    private String value;

    private Timestamp created;

    private Timestamp updated;

    public int getSystemSettingId() {
        return systemSettingId;
    }

    public void setSystemSettingId(int systemSettingId) {
        this.systemSettingId = systemSettingId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

}
