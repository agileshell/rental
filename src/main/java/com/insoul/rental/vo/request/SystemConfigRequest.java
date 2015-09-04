package com.insoul.rental.vo.request;

import java.io.Serializable;

public class SystemConfigRequest implements Serializable {

	private static final long serialVersionUID = -2412757419391172456L;

	private String watermeter;
	private String meter;

	private String first_quarter_start;
	private String first_quarter_end;

	private String second_quarter_start;
	private String second_quarter_end;

	private String third_quarter_start;
	private String third_quarter_end;

	private String fourth_quarter_start;
	private String fourth_quarter_end;

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

	public String getFirst_quarter_start() {
		return first_quarter_start;
	}

	public void setFirst_quarter_start(String first_quarter_start) {
		this.first_quarter_start = first_quarter_start;
	}

	public String getFirst_quarter_end() {
		return first_quarter_end;
	}

	public void setFirst_quarter_end(String first_quarter_end) {
		this.first_quarter_end = first_quarter_end;
	}

	public String getSecond_quarter_start() {
		return second_quarter_start;
	}

	public void setSecond_quarter_start(String second_quarter_start) {
		this.second_quarter_start = second_quarter_start;
	}

	public String getSecond_quarter_end() {
		return second_quarter_end;
	}

	public void setSecond_quarter_end(String second_quarter_end) {
		this.second_quarter_end = second_quarter_end;
	}

	public String getThird_quarter_start() {
		return third_quarter_start;
	}

	public void setThird_quarter_start(String third_quarter_start) {
		this.third_quarter_start = third_quarter_start;
	}

	public String getThird_quarter_end() {
		return third_quarter_end;
	}

	public void setThird_quarter_end(String third_quarter_end) {
		this.third_quarter_end = third_quarter_end;
	}

	public String getFourth_quarter_start() {
		return fourth_quarter_start;
	}

	public void setFourth_quarter_start(String fourth_quarter_start) {
		this.fourth_quarter_start = fourth_quarter_start;
	}

	public String getFourth_quarter_end() {
		return fourth_quarter_end;
	}

	public void setFourth_quarter_end(String fourth_quarter_end) {
		this.fourth_quarter_end = fourth_quarter_end;
	}

}
