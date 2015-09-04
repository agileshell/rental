package com.insoul.rental.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.insoul.rental.dao.FeeStatisticsDao;
import com.insoul.rental.dao.FlatDao;
import com.insoul.rental.dao.FlatFeeStatisticsDao;
import com.insoul.rental.dao.FlatMeterPaymentHistoryDao;
import com.insoul.rental.dao.FlatPaymentHistoryDao;
import com.insoul.rental.dao.FlatRenterDao;
import com.insoul.rental.dao.RenterDao;
import com.insoul.rental.dao.StallDao;
import com.insoul.rental.dao.StallFeeStatisticsDao;
import com.insoul.rental.dao.StallPaymentHistoryDao;
import com.insoul.rental.dao.StallRenterDao;
import com.insoul.rental.dao.StallUtilitiesPaymentHistoryDao;
import com.insoul.rental.dao.SubareaDao;
import com.insoul.rental.dao.SystemSettingDao;
import com.insoul.rental.service.BaseService;

@Service
public class BaseServiceImpl implements BaseService {

    @Resource
    protected RenterDao renterDao;

    @Resource
    protected StallDao stallDao;

    @Resource
    protected StallRenterDao stallRenterDao;

    @Resource
    protected StallPaymentHistoryDao stallPaymentHistoryDao;

    @Resource
    protected StallUtilitiesPaymentHistoryDao stallUtilitiesPaymentHistoryDao;

    @Resource
    protected FlatDao flatDao;

    @Resource
    protected FlatRenterDao flatRenterDao;

    @Resource
    protected FlatPaymentHistoryDao flatPaymentHistoryDao;

    @Resource
    protected FlatMeterPaymentHistoryDao flatMeterPaymentHistoryDao;

    @Resource
    protected SubareaDao subareaDao;

    @Resource
    protected SystemSettingDao systemSettingDao;

    @Resource
    protected StallFeeStatisticsDao stallFeeStatisticsDao;

    @Resource
    protected FlatFeeStatisticsDao flatFeeStatisticsDao;

    @Resource
    protected FeeStatisticsDao feeStatisticsDao;

    protected int getQuarter() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;

        if (3 <= month && month <= 5) {
            return 1;
        } else if (6 <= month && month <= 8) {
            return 2;
        } else if (9 <= month && month <= 11) {
            return 3;
        } else {
            return 4;
        }
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
}
