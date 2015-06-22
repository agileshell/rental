package com.insoul.rental.vo.request;

import java.io.Serializable;

public class SystemConfigRequest implements Serializable {

    private static final long serialVersionUID = -2412757419391172456L;

    private String watermeter;

    private String meter;

    public String getWatermeter() {
        return watermeter;
    }

    public void setWatermeter(String watermeter) {
        this.watermeter = watermeter;
    }

    public String getMeter() {
        return meter;
    }

    public void setMeter(String meter) {
        this.meter = meter;
    }

}
