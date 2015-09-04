package com.insoul.rental.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseController {

    String DATE_FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpSession session;

    protected boolean isLogin() {
        return (null != session.getAttribute("account")) ? true : false;
    }

    protected Map<String, Integer> getQuarterInfo(Date endDate) {
        Map<String, Integer> settings = new HashMap<String, Integer>();

        Calendar cal = Calendar.getInstance();
        cal.setTime(endDate);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;

        settings.put("year", year);
        settings.put("month", month);

        if (3 <= month && month <= 5) {
            settings.put("quarter", 1);
        } else if (6 <= month && month <= 8) {
            settings.put("quarter", 2);
        } else if (9 <= month && month <= 11) {
            settings.put("quarter", 3);
        } else {
            if (month != 12) {
                settings.put("year", year - 1);
            }
            settings.put("quarter", 4);
        }

        return settings;
    }

    protected Map<String, String> getQuarterStartEnd(Date date) {
        Map<String, String> settings = new HashMap<String, String>();

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;

        if (3 <= month && month <= 5) {
            String firstQuarterStart = year + "-03-01";
            String firstQuarterEnd = year + "-05-31";

            settings.put("startDate", firstQuarterStart);
            settings.put("endDate", firstQuarterEnd);
        } else if (6 <= month && month <= 8) {
            String secondQuarterStart = year + "-06-01";
            String secondQuarterEnd = year + "-08-31";

            settings.put("startDate", secondQuarterStart);
            settings.put("endDate", secondQuarterEnd);
        } else if (9 <= month && month <= 11) {
            String thirdQuarterStart = year + "-09-01";
            String thirdQuarterEnd = year + "-11-30";

            settings.put("startDate", thirdQuarterStart);
            settings.put("endDate", thirdQuarterEnd);
        } else {
            String fourthQuarterStart = null;
            String fourthQuarterEnd = null;
            if (month == 12) {
                fourthQuarterStart = year + "-12-01";
                fourthQuarterEnd = (year + 1) + "-02-28";
            } else {
                fourthQuarterStart = (year - 1) + "-12-01";
                fourthQuarterEnd = year + "-02-28";
            }

            settings.put("startDate", fourthQuarterStart);
            settings.put("endDate", fourthQuarterEnd);
        }

        return settings;
    }
}
