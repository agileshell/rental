package com.insoul.rental.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.insoul.rental.criteria.PaginationCriteria;
import com.insoul.rental.criteria.StallCriteria;
import com.insoul.rental.model.FeeStatistics;
import com.insoul.rental.model.Pagination;
import com.insoul.rental.model.Renter;
import com.insoul.rental.model.Stall;
import com.insoul.rental.model.StallFeeStatistics;
import com.insoul.rental.model.StallPaymentHistory;
import com.insoul.rental.model.StallRenter;
import com.insoul.rental.model.StallUtilitiesPaymentHistory;
import com.insoul.rental.model.Subarea;
import com.insoul.rental.service.StallService;
import com.insoul.rental.vo.CurrentPage;
import com.insoul.rental.vo.StallDetailVO;
import com.insoul.rental.vo.StallListVO;
import com.insoul.rental.vo.StallPaymentHistoryListVO;
import com.insoul.rental.vo.StallRenterDetailVO;
import com.insoul.rental.vo.StallUtilitiesPaymentHistoryListVO;
import com.insoul.rental.vo.SubareaVO;
import com.insoul.rental.vo.request.PaginationRequest;
import com.insoul.rental.vo.request.RentPayment;
import com.insoul.rental.vo.request.RenterCreateRequest;
import com.insoul.rental.vo.request.StallCreateRequest;
import com.insoul.rental.vo.request.StallListRequest;
import com.insoul.rental.vo.request.UtilitiesPayment;

@Service
public class StallServiceImpl extends BaseServiceImpl implements StallService {

    @Override
    public int createStall(StallCreateRequest stallCreateRequest) {
        Timestamp now = new Timestamp(System.currentTimeMillis());

        Stall stall = new Stall();
        stall.setSubareaId(stallCreateRequest.getSubareaId());
        stall.setName(stallCreateRequest.getName());
        stall.setMonthPrice(stallCreateRequest.getMonthPrice());
        stall.setComment(stallCreateRequest.getComment());
        stall.setCreated(now);
        int stallId = stallDao.create(stall);

        int renterId = stallCreateRequest.getRenterId();

        RenterCreateRequest renterCreateRequest = stallCreateRequest.getRenter();
        if (0 == renterId && null != renterCreateRequest.getName()) {
            Renter renter = new Renter();
            renter.setName(renterCreateRequest.getName());
            renter.setMobile(renterCreateRequest.getMobile());
            renter.setIdCard(renterCreateRequest.getIdCard());
            renter.setCreated(now);

            renterId = renterDao.create(renter);
        }

        if (0 != renterId) {
            StallRenter stallRenter = new StallRenter();
            stallRenter.setRenterId(renterId);
            stallRenter.setStallId(stallId);
            stallRenter.setRentDate(null == stallCreateRequest.getRentDate() ? new Date() : stallCreateRequest
                    .getRentDate());
            stallRenter.setUnrentDate(stallCreateRequest.getUnrentDate());
            stallRenter.setInitWatermeter(stallCreateRequest.getInitWatermeter());
            stallRenter.setInitMeter(stallCreateRequest.getInitMeter());
            stallRenter.setComment(stallCreateRequest.getRentComment());
            stallRenter.setCreated(now);
            int stallRenterId = stallRenterDao.create(stallRenter);

            stall.setStallId(stallId);
            stall.setRenterId(renterId);
            stall.setStallRenterId(stallRenterId);
            stallDao.update(stall);
        }

        return renterId;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public CurrentPage<StallListVO> listStalls(StallListRequest stallListRequest) {
        StallCriteria criteria = new StallCriteria();
        criteria.setName(stallListRequest.getName());
        criteria.setIsRented(stallListRequest.getIsRented());
        if (null != stallListRequest.getSubareaId()) {
            criteria.setSubareaId(stallListRequest.getSubareaId());
        }
        if (null != stallListRequest.getRenterId()) {
            criteria.setRenterId(stallListRequest.getRenterId());
        }

        int curn = stallListRequest.getCurn() > 0 ? stallListRequest.getCurn() : 1;
        int pageSize = stallListRequest.getPs();
        criteria.setOffset((curn - 1) * pageSize);
        criteria.setLimit(pageSize);

        Pagination<Stall> stalls = stallDao.listStalls(criteria);

        return new CurrentPage(curn, stalls.getCount(), pageSize, formatStalls(stalls.getItems()));
    }

    @Override
    public StallDetailVO getStallDetail(int stallId) {
        StallDetailVO stallVO = new StallDetailVO();

        Stall stall = stallDao.getById(stallId);
        if (null == stall) {
            return stallVO;
        }

        stallVO.setStallId(stall.getStallId());
        stallVO.setName(stall.getName());
        stallVO.setMonthPrice(stall.getMonthPrice());
        stallVO.setRenterId(stall.getRenterId());
        stallVO.setComment(stall.getComment());

        Subarea subarea = subareaDao.getById(stall.getSubareaId());
        SubareaVO subareaVO = new SubareaVO();
        subareaVO.setSubareaId(subarea.getSubareaId());
        subareaVO.setName(subarea.getName());
        subareaVO.setComment(subarea.getComment());
        stallVO.setSubarea(subareaVO);

        int stallRenterId = stall.getStallRenterId();
        if (0 != stallRenterId) {
            StallRenterDetailVO stallRenterVO = new StallRenterDetailVO();
            StallRenter stallRenter = stallRenterDao.getById(stallRenterId);
            if (null != stallRenter) {
                stallRenterVO.setStallRenterId(stallRenterId);
                stallRenterVO.setRenterId(stallRenter.getRenterId());
                stallRenterVO.setRentDate(stallRenter.getRentDate());
                stallRenterVO.setUnrentDate(stallRenter.getUnrentDate());
                stallRenterVO.setInitWatermeter(stallRenter.getInitWatermeter());
                stallRenterVO.setInitMeter(stallRenter.getInitMeter());
                stallRenterVO.setComment(stallRenter.getComment());
                stallRenterVO.setRenterName(stallRenter.getRenterName());
            }

            stallVO.setStallRenter(stallRenterVO);
        }

        return stallVO;
    }

    @Override
    public void editStall(int stallId, StallCreateRequest stallCreateRequest) {
        Timestamp now = new Timestamp(System.currentTimeMillis());

        Stall stall = stallDao.getById(stallId);
        if (null == stall) {
            return;
        }

        stall.setSubareaId(stallCreateRequest.getSubareaId());
        stall.setName(stallCreateRequest.getName());
        stall.setMonthPrice(stallCreateRequest.getMonthPrice());
        stall.setComment(stallCreateRequest.getComment());
        stall.setUpdated(now);

        int renterId = stallCreateRequest.getRenterId();
        if (0 != renterId) {
            if (renterId != stall.getRenterId()) {
                StallRenter stallRenter = new StallRenter();
                stallRenter.setRenterId(renterId);
                stallRenter.setStallId(stallId);
                stallRenter.setRentDate(null == stallCreateRequest.getRentDate() ? new Date() : stallCreateRequest
                        .getRentDate());
                stallRenter.setUnrentDate(stallCreateRequest.getUnrentDate());
                stallRenter.setInitWatermeter(stallCreateRequest.getInitWatermeter());
                stallRenter.setInitMeter(stallCreateRequest.getInitMeter());
                stallRenter.setComment(stallCreateRequest.getRentComment());
                stallRenter.setCreated(now);
                int stallRenterId = stallRenterDao.create(stallRenter);

                stall.setStallRenterId(stallRenterId);
            } else {
                int stallRenterId = stall.getStallRenterId();
                StallRenter stallRenter = stallRenterDao.getById(stallRenterId);
                if (null != stallRenter) {
                    stallRenter.setRentDate(null == stallCreateRequest.getRentDate() ? new Date() : stallCreateRequest
                            .getRentDate());
                    stallRenter.setUnrentDate(stallCreateRequest.getUnrentDate());
                    stallRenter.setInitWatermeter(stallCreateRequest.getInitWatermeter());
                    stallRenter.setInitMeter(stallCreateRequest.getInitMeter());
                    stallRenter.setComment(stallCreateRequest.getRentComment());

                    stallRenterDao.update(stallRenter);
                }
            }

            stall.setRenterId(renterId);
        } else {
            if (0 != stall.getStallRenterId()) {
                stallRenterDao.unRent(stall.getStallRenterId());
            }

            stall.setStallRenterId(0);
            stall.setRenterId(0);
        }

        stallDao.update(stall);
    }

    @Override
    public void unrent(int stallId) {
        Stall stall = stallDao.getById(stallId);
        if (null == stall) {
            return;
        }

        stallDao.unRent(stallId);
        if (0 != stall.getStallRenterId()) {
            stallRenterDao.unRent(stall.getStallRenterId());
        }
    }

    @Override
    public int deleteStall(int stallId) {
        Stall stall = stallDao.getById(stallId);
        if (null == stall) {
            return -1;
        }
        if (0 != stall.getStallRenterId()) {
            return -2;
        }

        stallDao.delete(stallId);

        return 1;
    }

    private List<StallListVO> formatStalls(List<Stall> stalls) {
        List<StallListVO> stallVOs = new ArrayList<StallListVO>();
        if (null == stalls || stalls.isEmpty()) {
            return stallVOs;
        }

        List<Integer> subareaIds = new ArrayList<Integer>();
        for (Stall stall : stalls) {
            subareaIds.add(stall.getSubareaId());
        }
        Map<Integer, String> subareaIdSubareaNameMap = new HashMap<Integer, String>();
        List<Subarea> subareas = subareaDao.getSubareasByIds(subareaIds);
        for (Subarea subarea : subareas) {
            subareaIdSubareaNameMap.put(subarea.getSubareaId(), subarea.getName());
        }

        Map<Integer, StallListVO> stallMap = new HashMap<Integer, StallListVO>();
        List<Integer> stallRenterIds = new ArrayList<Integer>();

        for (Stall stall : stalls) {
            StallListVO vo = new StallListVO();
            vo.setStallId(stall.getStallId());
            vo.setSubarea(subareaIdSubareaNameMap.get(stall.getSubareaId()));
            vo.setName(stall.getName());
            vo.setMonthPrice(stall.getMonthPrice());
            vo.setRenterId(stall.getRenterId());
            vo.setRenterName(stall.getRenterName());
            vo.setCreated(stall.getCreated());

            stallVOs.add(vo);

            int stallRenterId = stall.getStallRenterId();
            stallMap.put(stallRenterId, vo);
            stallRenterIds.add(stallRenterId);
        }

        Map<String, Integer> quarterInfo = getQuarterInfo(new Date());
        int quarter = quarterInfo.get("quarter");
        String year = quarterInfo.get("year") + "";

        List<StallFeeStatistics> stallFees = stallFeeStatisticsDao
                .findStallFeeStatistics(stallRenterIds, year, quarter);
        for (StallFeeStatistics stallFee : stallFees) {
            if (stallMap.containsKey(stallFee.getStallRenterId())) {
                StallListVO stallListVO = stallMap.get(stallFee.getStallRenterId());
                stallListVO.setIsPayRent(stallFee.getIsPayRent());
                stallListVO.setIsPayMeter(stallFee.getIsPayMeter());
                stallListVO.setIsPayWater(stallFee.getIsPayWater());
                stallListVO.setStatisticId(stallFee.getId());
            }
        }

        return stallVOs;
    }

    @Override
    public void payRent(int stallId, RentPayment rentPayment) {
        Stall stall = stallDao.getById(stallId);
        if (null == stall || 0 == stall.getStallRenterId()) {
            return;
        }

        Map<String, Integer> quarterInfo = getQuarterInfo(rentPayment.getEndDate());
        int quarter = quarterInfo.get("quarter");
        String year = quarterInfo.get("year") + "";
        String month = quarterInfo.get("month") + "";

        Timestamp now = new Timestamp(System.currentTimeMillis());

        StallPaymentHistory stallPaymentHistory = new StallPaymentHistory();
        stallPaymentHistory.setStallRenterId(stall.getStallRenterId());
        stallPaymentHistory.setStartDate(rentPayment.getStartDate());
        stallPaymentHistory.setEndDate(rentPayment.getEndDate());
        stallPaymentHistory.setTotalPrice(rentPayment.getTotalPrice());
        stallPaymentHistory.setComment(rentPayment.getComment());
        stallPaymentHistory.setCreated(now);
        stallPaymentHistory.setQuarter(quarter);

        stallPaymentHistoryDao.create(stallPaymentHistory);

        StallFeeStatistics statistic = stallFeeStatisticsDao.getStallFeeStatistic(stall.getStallRenterId(), year,
                quarter);
        if (null != statistic) {
            statistic.setIsPayRent(1);
            statistic.setRentFee(rentPayment.getTotalPrice());
            statistic.setTotalFee(statistic.getTotalFee() + rentPayment.getTotalPrice());
            statistic.setUpdated(now);

            stallFeeStatisticsDao.update(statistic);
        } else {
            statistic = new StallFeeStatistics();
            statistic.setStallRenterId(stall.getStallRenterId());
            statistic.setYear(year);
            statistic.setQuarter(quarter);

            statistic.setIsPayRent(1);
            statistic.setRentFee(rentPayment.getTotalPrice());
            statistic.setTotalFee(rentPayment.getTotalPrice());
            statistic.setCreated(now);

            stallFeeStatisticsDao.create(statistic);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String recordDate = sdf.format(now);
        FeeStatistics feeStatistic = feeStatisticsDao.getFeeStatistic(recordDate);
        if (null == feeStatistic) {
            feeStatistic = new FeeStatistics();
            feeStatistic.setRecordDate(recordDate);
            feeStatistic.setYear(year);
            feeStatistic.setMonth(month);
            feeStatistic.setQuarter(quarter);
            feeStatistic.setCreated(now);

            feeStatisticsDao.create(feeStatistic);
        }
    }

    @Override
    public void payWatermeter(int stallId, UtilitiesPayment utilitiesPayment) {
        payUtilities(stallId, utilitiesPayment, 1);
    }

    @Override
    public void payMeter(int stallId, UtilitiesPayment utilitiesPayment) {
        payUtilities(stallId, utilitiesPayment, 2);
    }

    private void payUtilities(int stallId, UtilitiesPayment utilitiesPayment, int type) {
        Stall stall = stallDao.getById(stallId);
        if (null == stall || 0 == stall.getStallRenterId()) {
            return;
        }

        Map<String, Integer> quarterInfo = getQuarterInfo(utilitiesPayment.getRecordDate());
        int quarter = quarterInfo.get("quarter");
        String year = quarterInfo.get("year") + "";
        String month = quarterInfo.get("month") + "";
        Timestamp now = new Timestamp(System.currentTimeMillis());

        StallUtilitiesPaymentHistory stallUtilitiesPaymentHistory = new StallUtilitiesPaymentHistory();
        stallUtilitiesPaymentHistory.setStallRenterId(stall.getStallRenterId());
        stallUtilitiesPaymentHistory.setFirstRecord(utilitiesPayment.getFirstRecord());
        stallUtilitiesPaymentHistory.setLastRecord(utilitiesPayment.getLastRecord());
        stallUtilitiesPaymentHistory.setPrice(utilitiesPayment.getPrice());
        stallUtilitiesPaymentHistory.setTotalPrice(utilitiesPayment.getTotalPrice());
        stallUtilitiesPaymentHistory.setComment(utilitiesPayment.getComment());
        stallUtilitiesPaymentHistory.setRecordDate(utilitiesPayment.getRecordDate());
        stallUtilitiesPaymentHistory.setCreated(now);
        stallUtilitiesPaymentHistory.setType(type);
        stallUtilitiesPaymentHistory.setQuarter(quarter);

        stallUtilitiesPaymentHistoryDao.create(stallUtilitiesPaymentHistory);

        StallFeeStatistics statistic = stallFeeStatisticsDao.getStallFeeStatistic(stall.getStallRenterId(), year,
                quarter);
        if (null != statistic) {
            if (type == 1) {
                statistic.setIsPayWater(1);
                statistic.setWaterFee(utilitiesPayment.getTotalPrice());
            } else {
                statistic.setIsPayMeter(1);
                statistic.setMeterFee(utilitiesPayment.getTotalPrice());
            }
            statistic.setTotalFee(statistic.getTotalFee() + utilitiesPayment.getTotalPrice());
            statistic.setUpdated(now);

            stallFeeStatisticsDao.update(statistic);
        } else {
            statistic = new StallFeeStatistics();
            statistic.setStallRenterId(stall.getStallRenterId());
            statistic.setYear(year);
            statistic.setQuarter(quarter);

            if (type == 1) {
                statistic.setIsPayWater(1);
                statistic.setWaterFee(utilitiesPayment.getTotalPrice());
            } else {
                statistic.setIsPayMeter(1);
                statistic.setMeterFee(utilitiesPayment.getTotalPrice());
            }
            statistic.setTotalFee(utilitiesPayment.getTotalPrice());
            statistic.setCreated(now);

            stallFeeStatisticsDao.create(statistic);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String recordDate = sdf.format(now);
        FeeStatistics feeStatistic = feeStatisticsDao.getFeeStatistic(recordDate);
        if (null == feeStatistic) {
            feeStatistic = new FeeStatistics();
            feeStatistic.setRecordDate(recordDate);
            feeStatistic.setYear(year);
            feeStatistic.setMonth(month);
            feeStatistic.setQuarter(quarter);
            feeStatistic.setCreated(now);

            feeStatisticsDao.create(feeStatistic);
        }
    }

    @Override
    public int confirmPay(int statisticId, String type) {
        StallFeeStatistics statistic = stallFeeStatisticsDao.getStallFeeStatistic(statisticId);
        if (null == statistic) {
            return 0;
        }

        Timestamp now = new Timestamp(System.currentTimeMillis());

        if (type.equals("rent")) {
            statistic.setIsPayRent(2);
            if (statistic.getIsPayMeter() == 2 && statistic.getIsPayWater() == 2) {
                statistic.setIsPayAll(2);
            }
        } else if (type.equals("meter")) {
            statistic.setIsPayMeter(2);
            if (statistic.getIsPayRent() == 2 && statistic.getIsPayWater() == 2) {
                statistic.setIsPayAll(2);
            }
        } else if (type.equals("water")) {
            statistic.setIsPayWater(2);
            if (statistic.getIsPayRent() == 2 && statistic.getIsPayMeter() == 2) {
                statistic.setIsPayAll(2);
            }
        }
        statistic.setUpdated(now);
        stallFeeStatisticsDao.update(statistic);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String recordDate = sdf.format(now);
        FeeStatistics feeStatistic = feeStatisticsDao.getFeeStatistic(recordDate);
        if (null != feeStatistic) {
            if (type.equals("rent")) {
                feeStatistic.setStallRentFee(feeStatistic.getStallWaterFee() + statistic.getRentFee());
                feeStatistic.setTotalFee(feeStatistic.getTotalFee() + statistic.getRentFee());
            } else if (type.equals("meter")) {
                feeStatistic.setStallMeterFee(feeStatistic.getStallMeterFee() + statistic.getMeterFee());
                feeStatistic.setTotalFee(feeStatistic.getTotalFee() + statistic.getMeterFee());
            } else if (type.equals("water")) {
                feeStatistic.setStallWaterFee(feeStatistic.getStallWaterFee() + statistic.getWaterFee());
                feeStatistic.setTotalFee(feeStatistic.getTotalFee() + statistic.getWaterFee());
            }

            feeStatistic.setUpdated(now);

            feeStatisticsDao.update(feeStatistic);
        }

        return 1;
    }

    @Override
    public String getLastWatermeter(int stallRenterId) {
        List<StallUtilitiesPaymentHistory> histories = stallUtilitiesPaymentHistoryDao.getStallUtilitiesPaymentHistory(
                stallRenterId, 1);
        if (null != histories && histories.size() > 0) {
            return histories.get(0).getLastRecord();
        }

        return null;
    }

    @Override
    public String getLastMeter(int stallRenterId) {
        List<StallUtilitiesPaymentHistory> histories = stallUtilitiesPaymentHistoryDao.getStallUtilitiesPaymentHistory(
                stallRenterId, 2);
        if (null != histories && histories.size() > 0) {
            return histories.get(0).getLastRecord();
        }

        return null;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public CurrentPage<StallPaymentHistoryListVO> listStallPaymentHistory(int stallId,
            PaginationRequest paginationRequest) {
        int curn = paginationRequest.getCurn() > 0 ? paginationRequest.getCurn() : 1;
        int pageSize = paginationRequest.getPs();
        PaginationCriteria pagination = new PaginationCriteria();
        pagination.setOffset((curn - 1) * pageSize);
        pagination.setLimit(pageSize);

        Pagination<StallPaymentHistory> stallPaymentHistories = stallPaymentHistoryDao.listStallPaymentHistory(stallId,
                pagination);

        return new CurrentPage(curn, stallPaymentHistories.getCount(), pageSize,
                formatStallPaymentHistories(stallPaymentHistories.getItems()));
    }

    private List<StallPaymentHistoryListVO> formatStallPaymentHistories(List<StallPaymentHistory> stallPaymentHistories) {
        List<StallPaymentHistoryListVO> stallPaymentHistoryListVOs = new ArrayList<StallPaymentHistoryListVO>();
        if (null == stallPaymentHistories || stallPaymentHistories.isEmpty()) {
            return stallPaymentHistoryListVOs;
        }

        for (StallPaymentHistory stallPaymentHistory : stallPaymentHistories) {
            StallPaymentHistoryListVO vo = new StallPaymentHistoryListVO();
            vo.setId(stallPaymentHistory.getId());
            vo.setStartDate(stallPaymentHistory.getStartDate());
            vo.setEndDate(stallPaymentHistory.getEndDate());
            vo.setTotalPrice(stallPaymentHistory.getTotalPrice());
            vo.setQuarter(stallPaymentHistory.getQuarter());
            vo.setStallId(stallPaymentHistory.getStallId());
            vo.setRenterId(stallPaymentHistory.getRenterId());
            vo.setRenterName(stallPaymentHistory.getRenterName());
            vo.setCreated(stallPaymentHistory.getCreated());

            stallPaymentHistoryListVOs.add(vo);
        }

        return stallPaymentHistoryListVOs;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public CurrentPage<StallUtilitiesPaymentHistoryListVO> listStallUtilitiesPaymentHistory(int stallId, int type,
            PaginationRequest paginationRequest) {
        int curn = paginationRequest.getCurn() > 0 ? paginationRequest.getCurn() : 1;
        int pageSize = paginationRequest.getPs();
        PaginationCriteria pagination = new PaginationCriteria();
        pagination.setOffset((curn - 1) * pageSize);
        pagination.setLimit(pageSize);

        Pagination<StallUtilitiesPaymentHistory> stallUtilitiesPaymentHistories = stallUtilitiesPaymentHistoryDao
                .listStallUtilitiesPaymentHistory(stallId, type, pagination);

        return new CurrentPage(curn, stallUtilitiesPaymentHistories.getCount(), pageSize,
                formatStallUtilitiesPaymentHistories(stallUtilitiesPaymentHistories.getItems()));
    }

    private List<StallUtilitiesPaymentHistoryListVO> formatStallUtilitiesPaymentHistories(
            List<StallUtilitiesPaymentHistory> stallUtilitiesPaymentHistories) {
        List<StallUtilitiesPaymentHistoryListVO> stallUtilitiesPaymentHistoryListVOs = new ArrayList<StallUtilitiesPaymentHistoryListVO>();
        if (null == stallUtilitiesPaymentHistories || stallUtilitiesPaymentHistories.isEmpty()) {
            return stallUtilitiesPaymentHistoryListVOs;
        }

        for (StallUtilitiesPaymentHistory history : stallUtilitiesPaymentHistories) {
            StallUtilitiesPaymentHistoryListVO vo = new StallUtilitiesPaymentHistoryListVO();
            vo.setId(history.getId());
            vo.setFirstRecord(history.getFirstRecord());
            vo.setLastRecord(history.getLastRecord());
            vo.setPrice(history.getPrice());
            vo.setTotalPrice(history.getTotalPrice());
            vo.setQuarter(history.getQuarter());
            vo.setRecordDate(history.getRecordDate());
            vo.setRenterId(history.getRenterId());
            vo.setRenterName(history.getRenterName());
            vo.setCreated(history.getCreated());

            stallUtilitiesPaymentHistoryListVOs.add(vo);
        }

        return stallUtilitiesPaymentHistoryListVOs;
    }

    @Override
    public void generateStallFeeStatistics(String year, int quarter) {
        Timestamp now = new Timestamp(System.currentTimeMillis());

        List<Integer> stallRenterIds = stallFeeStatisticsDao.findStallRenterIds(year, quarter);
        for (Integer stallRenterId : stallRenterIds) {
            StallFeeStatistics statistic = new StallFeeStatistics();
            statistic.setStallRenterId(stallRenterId);
            statistic.setYear(year);
            statistic.setQuarter(quarter);
            statistic.setCreated(now);

            stallFeeStatisticsDao.create(statistic);
        }
    }
}
