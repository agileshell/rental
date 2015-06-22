package com.insoul.rental.service.impl;

import java.util.Calendar;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.insoul.rental.dao.FlatDao;
import com.insoul.rental.dao.FlatMeterPaymentHistoryDao;
import com.insoul.rental.dao.FlatPaymentHistoryDao;
import com.insoul.rental.dao.FlatRenterDao;
import com.insoul.rental.dao.RenterDao;
import com.insoul.rental.dao.StallDao;
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

    protected int getQuarter() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;

        if (2 <= month && month <= 4) {
            return 1;
        } else if (5 <= month && month <= 7) {
            return 2;
        } else if (8 <= month && month <= 10) {
            return 3;
        } else {
            return 4;
        }
    }
}
