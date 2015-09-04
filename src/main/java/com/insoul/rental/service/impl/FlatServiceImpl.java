package com.insoul.rental.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.insoul.rental.criteria.FlatCriteria;
import com.insoul.rental.criteria.PaginationCriteria;
import com.insoul.rental.model.FeeStatistics;
import com.insoul.rental.model.Flat;
import com.insoul.rental.model.FlatFeeStatistics;
import com.insoul.rental.model.FlatMeterPaymentHistory;
import com.insoul.rental.model.FlatPaymentHistory;
import com.insoul.rental.model.FlatRenter;
import com.insoul.rental.model.Pagination;
import com.insoul.rental.service.FlatService;
import com.insoul.rental.vo.CurrentPage;
import com.insoul.rental.vo.FlatDetailVO;
import com.insoul.rental.vo.FlatListVO;
import com.insoul.rental.vo.FlatMeterPaymentHistoryListVO;
import com.insoul.rental.vo.FlatPaymentHistoryListVO;
import com.insoul.rental.vo.FlatRenterDetailVO;
import com.insoul.rental.vo.request.FlatCreateRequest;
import com.insoul.rental.vo.request.FlatListRequest;
import com.insoul.rental.vo.request.PaginationRequest;
import com.insoul.rental.vo.request.RentPayment;
import com.insoul.rental.vo.request.UtilitiesPayment;

@Service
public class FlatServiceImpl extends BaseServiceImpl implements FlatService {

    @Override
    public int createFlat(FlatCreateRequest flatCreateRequest) {
        Timestamp now = new Timestamp(System.currentTimeMillis());

        Flat flat = new Flat();
        flat.setName(flatCreateRequest.getName());
        flat.setMonthPrice(flatCreateRequest.getMonthPrice());
        flat.setComment(flatCreateRequest.getComment());
        flat.setCreated(now);
        int flatId = flatDao.create(flat);

        int renterId = flatCreateRequest.getRenterId();
        if (0 != flatId) {
            FlatRenter flatRenter = new FlatRenter();
            flatRenter.setFlatId(flatId);
            flatRenter.setRenterId(renterId);
            flatRenter.setRentDate(null == flatCreateRequest.getRentDate() ? new Date() : flatCreateRequest
                    .getRentDate());
            flatRenter.setUnrentDate(flatCreateRequest.getUnrentDate());
            flatRenter.setInitMeter(flatCreateRequest.getInitMeter());
            flatRenter.setComment(flatCreateRequest.getRentComment());
            flatRenter.setCreated(now);
            int flatRenterId = flatRenterDao.create(flatRenter);

            flat.setFlatId(flatId);
            flat.setRenterId(renterId);
            flat.setFlatRenterId(flatRenterId);
            flatDao.update(flat);
        }

        return flatId;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public CurrentPage<FlatListVO> listFlats(FlatListRequest flatListRequest) {
        FlatCriteria criteria = new FlatCriteria();
        criteria.setName(flatListRequest.getName());
        criteria.setIsRented(flatListRequest.getIsRented());
        if (null != flatListRequest.getRenterId()) {
            criteria.setRenterId(flatListRequest.getRenterId());
        }

        int curn = flatListRequest.getCurn() > 0 ? flatListRequest.getCurn() : 1;
        int pageSize = flatListRequest.getPs();
        criteria.setOffset((curn - 1) * pageSize);
        criteria.setLimit(pageSize);

        Pagination<Flat> flats = flatDao.listFlats(criteria);

        return new CurrentPage(curn, flats.getCount(), pageSize, formatFlats(flats.getItems()));
    }

    @Override
    public FlatDetailVO getFlatDetail(int flatId) {
        FlatDetailVO flatVO = new FlatDetailVO();

        Flat flat = flatDao.getById(flatId);
        if (null == flat) {
            return flatVO;
        }

        flatVO.setFlatId(flat.getFlatId());
        flatVO.setName(flat.getName());
        flatVO.setMonthPrice(flat.getMonthPrice());
        flatVO.setRenterId(flat.getRenterId());
        flatVO.setComment(flat.getComment());

        int flatRenterId = flat.getFlatRenterId();
        if (0 != flatRenterId) {
            FlatRenterDetailVO flatRenterVO = new FlatRenterDetailVO();
            FlatRenter flatRenter = flatRenterDao.getById(flatRenterId);
            if (null != flatRenter) {
                flatRenterVO.setFlatRenterId(flatRenterId);
                flatRenterVO.setRenterId(flatRenter.getRenterId());
                flatRenterVO.setRentDate(flatRenter.getRentDate());
                flatRenterVO.setUnrentDate(flatRenter.getUnrentDate());
                flatRenterVO.setInitMeter(flatRenter.getInitMeter());
                flatRenterVO.setComment(flatRenter.getComment());
                flatRenterVO.setRenterName(flatRenter.getRenterName());
            }

            flatVO.setFlatRenter(flatRenterVO);
        }

        return flatVO;
    }

    @Override
    public void editFlat(int flatId, FlatCreateRequest flatCreateRequest) {
        Timestamp now = new Timestamp(System.currentTimeMillis());

        Flat flat = flatDao.getById(flatId);
        if (null == flat) {
            return;
        }

        flat.setName(flatCreateRequest.getName());
        flat.setMonthPrice(flatCreateRequest.getMonthPrice());
        flat.setComment(flatCreateRequest.getComment());
        flat.setUpdated(now);

        int renterId = flatCreateRequest.getRenterId();
        if (0 != renterId) {
            if (renterId != flat.getRenterId()) {
                FlatRenter flatRenter = new FlatRenter();
                flatRenter.setRenterId(renterId);
                flatRenter.setFlatId(flatId);
                flatRenter.setRentDate(null == flatCreateRequest.getRentDate() ? new Date() : flatCreateRequest
                        .getRentDate());
                flatRenter.setUnrentDate(flatCreateRequest.getUnrentDate());
                flatRenter.setInitMeter(flatCreateRequest.getInitMeter());
                flatRenter.setComment(flatCreateRequest.getRentComment());
                flatRenter.setCreated(now);
                int flatRenterId = flatRenterDao.create(flatRenter);

                flat.setFlatRenterId(flatRenterId);
            } else {
                int flatRenterId = flat.getFlatRenterId();
                FlatRenter flatRenter = flatRenterDao.getById(flatRenterId);
                if (null != flatRenter) {
                    flatRenter.setRentDate(null == flatCreateRequest.getRentDate() ? new Date() : flatCreateRequest
                            .getRentDate());
                    flatRenter.setUnrentDate(flatCreateRequest.getUnrentDate());
                    flatRenter.setInitMeter(flatCreateRequest.getInitMeter());
                    flatRenter.setComment(flatCreateRequest.getRentComment());

                    flatRenterDao.update(flatRenter);
                }
            }

            flat.setRenterId(renterId);
        } else {
            if (0 != flat.getFlatRenterId()) {
                flatRenterDao.unRent(flat.getFlatRenterId());
            }

            flat.setFlatRenterId(0);
            flat.setRenterId(0);
        }

        flatDao.update(flat);
    }

    @Override
    public void unrent(int flatId) {
        Flat flat = flatDao.getById(flatId);
        if (null == flat) {
            return;
        }

        flatDao.unRent(flatId);
        if (0 != flat.getFlatRenterId()) {
            flatRenterDao.unRent(flat.getFlatRenterId());
        }
    }

    @Override
    public int deleteFlat(int flatId) {
        Flat flat = flatDao.getById(flatId);
        if (null == flat) {
            return -1;
        }
        if (0 != flat.getFlatRenterId()) {
            return -2;
        }

        flatDao.delete(flatId);

        return 1;
    }

    private List<FlatListVO> formatFlats(List<Flat> flats) {
        List<FlatListVO> flatVOs = new ArrayList<FlatListVO>();
        if (null == flats || flats.isEmpty()) {
            return flatVOs;
        }

        Map<Integer, FlatListVO> flatMap = new HashMap<Integer, FlatListVO>();
        List<Integer> flatRenterIds = new ArrayList<Integer>();

        for (Flat flat : flats) {
            FlatListVO vo = new FlatListVO();
            vo.setFlatId(flat.getFlatId());
            vo.setName(flat.getName());
            vo.setMonthPrice(flat.getMonthPrice());
            vo.setRenterId(flat.getRenterId());
            vo.setRenterName(flat.getRenterName());
            vo.setCreated(flat.getCreated());

            flatVOs.add(vo);

            int flatRenterId = flat.getFlatRenterId();
            flatMap.put(flatRenterId, vo);
            flatRenterIds.add(flatRenterId);
        }

        Map<String, Integer> quarterInfo = getQuarterInfo(new Date());
        int quarter = quarterInfo.get("quarter");
        String year = quarterInfo.get("year") + "";

        List<FlatFeeStatistics> flatFees = flatFeeStatisticsDao.findFlatFeeStatistics(flatRenterIds, year, quarter);
        for (FlatFeeStatistics flatFee : flatFees) {
            if (flatMap.containsKey(flatFee.getFlatRenterId())) {
                FlatListVO flatListVO = flatMap.get(flatFee.getFlatRenterId());
                flatListVO.setIsPayRent(flatFee.getIsPayRent());
                flatListVO.setIsPayMeter(flatFee.getIsPayMeter());
                flatListVO.setStatisticId(flatFee.getId());
            }
        }

        return flatVOs;
    }

    @Override
    public void payRent(int flatId, RentPayment rentPayment) {
        Flat flat = flatDao.getById(flatId);
        if (null == flat || 0 == flat.getFlatRenterId()) {
            return;
        }

        Map<String, Integer> quarterInfo = getQuarterInfo(rentPayment.getEndDate());
        int quarter = quarterInfo.get("quarter");
        String year = quarterInfo.get("year") + "";
        String month = quarterInfo.get("month") + "";

        Timestamp now = new Timestamp(System.currentTimeMillis());

        FlatPaymentHistory flatPaymentHistory = new FlatPaymentHistory();
        flatPaymentHistory.setFlatRenterId(flat.getFlatRenterId());
        flatPaymentHistory.setStartDate(rentPayment.getStartDate());
        flatPaymentHistory.setEndDate(rentPayment.getEndDate());
        flatPaymentHistory.setTotalPrice(rentPayment.getTotalPrice());
        flatPaymentHistory.setComment(rentPayment.getComment());
        flatPaymentHistory.setCreated(now);

        flatPaymentHistory.setQuarter(quarter);

        flatPaymentHistoryDao.create(flatPaymentHistory);

        FlatFeeStatistics statistic = flatFeeStatisticsDao.getFlatFeeStatistic(flat.getFlatRenterId(), year, quarter);
        if (null != statistic) {
            statistic.setIsPayRent(1);
            statistic.setRentFee(rentPayment.getTotalPrice());
            statistic.setTotalFee(statistic.getTotalFee() + rentPayment.getTotalPrice());
            statistic.setUpdated(now);

            flatFeeStatisticsDao.update(statistic);
        } else {
            statistic = new FlatFeeStatistics();
            statistic.setFlatRenterId(flat.getFlatRenterId());
            statistic.setYear(year);
            statistic.setQuarter(quarter);

            statistic.setIsPayRent(1);
            statistic.setRentFee(rentPayment.getTotalPrice());
            statistic.setTotalFee(rentPayment.getTotalPrice());
            statistic.setCreated(now);

            flatFeeStatisticsDao.create(statistic);
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

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public CurrentPage<FlatPaymentHistoryListVO> listFlatPaymentHistory(int flatId, PaginationRequest paginationRequest) {
        int curn = paginationRequest.getCurn() > 0 ? paginationRequest.getCurn() : 1;
        int pageSize = paginationRequest.getPs();
        PaginationCriteria pagination = new PaginationCriteria();
        pagination.setOffset((curn - 1) * pageSize);
        pagination.setLimit(pageSize);

        Pagination<FlatPaymentHistory> flatPaymentHistories = flatPaymentHistoryDao.listFlatPaymentHistory(flatId,
                pagination);

        return new CurrentPage(curn, flatPaymentHistories.getCount(), pageSize,
                formatFlatPaymentHistories(flatPaymentHistories.getItems()));
    }

    private List<FlatPaymentHistoryListVO> formatFlatPaymentHistories(List<FlatPaymentHistory> flatPaymentHistories) {
        List<FlatPaymentHistoryListVO> flatPaymentHistoryListVOs = new ArrayList<FlatPaymentHistoryListVO>();
        if (null == flatPaymentHistories || flatPaymentHistories.isEmpty()) {
            return flatPaymentHistoryListVOs;
        }

        for (FlatPaymentHistory flatPaymentHistory : flatPaymentHistories) {
            FlatPaymentHistoryListVO vo = new FlatPaymentHistoryListVO();
            vo.setId(flatPaymentHistory.getId());
            vo.setStartDate(flatPaymentHistory.getStartDate());
            vo.setEndDate(flatPaymentHistory.getEndDate());
            vo.setTotalPrice(flatPaymentHistory.getTotalPrice());
            vo.setQuarter(flatPaymentHistory.getQuarter());
            vo.setFlatId(flatPaymentHistory.getFlatId());
            vo.setRenterId(flatPaymentHistory.getRenterId());
            vo.setRenterName(flatPaymentHistory.getRenterName());
            vo.setCreated(flatPaymentHistory.getCreated());

            flatPaymentHistoryListVOs.add(vo);
        }

        return flatPaymentHistoryListVOs;
    }

    @Override
    public void payMeter(int flatId, UtilitiesPayment utilitiesPayment) {
        Flat flat = flatDao.getById(flatId);
        if (null == flat || 0 == flat.getFlatRenterId()) {
            return;
        }

        Map<String, Integer> quarterInfo = getQuarterInfo(utilitiesPayment.getRecordDate());
        int quarter = quarterInfo.get("quarter");
        String year = quarterInfo.get("year") + "";
        String month = quarterInfo.get("month") + "";
        Timestamp now = new Timestamp(System.currentTimeMillis());

        FlatMeterPaymentHistory flatMeterPaymentHistory = new FlatMeterPaymentHistory();
        flatMeterPaymentHistory.setFlatRenterId(flat.getFlatRenterId());
        flatMeterPaymentHistory.setFirstRecord(utilitiesPayment.getFirstRecord());
        flatMeterPaymentHistory.setLastRecord(utilitiesPayment.getLastRecord());
        flatMeterPaymentHistory.setPrice(utilitiesPayment.getPrice());
        flatMeterPaymentHistory.setTotalPrice(utilitiesPayment.getTotalPrice());
        flatMeterPaymentHistory.setComment(utilitiesPayment.getComment());
        flatMeterPaymentHistory.setRecordDate(utilitiesPayment.getRecordDate());
        flatMeterPaymentHistory.setCreated(now);

        flatMeterPaymentHistory.setQuarter(quarter);

        flatMeterPaymentHistoryDao.create(flatMeterPaymentHistory);

        FlatFeeStatistics statistic = flatFeeStatisticsDao.getFlatFeeStatistic(flat.getFlatRenterId(), year, quarter);
        if (null != statistic) {
            statistic.setIsPayMeter(1);
            statistic.setMeterFee(utilitiesPayment.getTotalPrice());
            statistic.setTotalFee(statistic.getTotalFee() + utilitiesPayment.getTotalPrice());
            statistic.setUpdated(now);

            flatFeeStatisticsDao.update(statistic);
        } else {
            statistic = new FlatFeeStatistics();
            statistic.setFlatRenterId(flat.getFlatRenterId());
            statistic.setYear(year);
            statistic.setQuarter(quarter);

            statistic.setIsPayMeter(1);
            statistic.setMeterFee(utilitiesPayment.getTotalPrice());
            statistic.setTotalFee(utilitiesPayment.getTotalPrice());
            statistic.setCreated(now);

            flatFeeStatisticsDao.create(statistic);
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
        FlatFeeStatistics statistic = flatFeeStatisticsDao.getFlatFeeStatistic(statisticId);
        if (null == statistic) {
            return 0;
        }

        Timestamp now = new Timestamp(System.currentTimeMillis());

        if (type.equals("rent")) {
            statistic.setIsPayRent(2);
            if (statistic.getIsPayMeter() == 2) {
                statistic.setIsPayAll(2);
            }
        } else if (type.equals("meter")) {
            statistic.setIsPayMeter(2);
            if (statistic.getIsPayRent() == 2) {
                statistic.setIsPayAll(2);
            }
        }
        statistic.setUpdated(now);
        flatFeeStatisticsDao.update(statistic);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String recordDate = sdf.format(now);
        FeeStatistics feeStatistic = feeStatisticsDao.getFeeStatistic(recordDate);
        if (null != feeStatistic) {
            if (type.equals("rent")) {
                feeStatistic.setFlatRentFee(feeStatistic.getFlatRentFee() + statistic.getRentFee());
                feeStatistic.setTotalFee(feeStatistic.getTotalFee() + statistic.getRentFee());
            } else if (type.equals("meter")) {
                feeStatistic.setFlatMeterFee(feeStatistic.getFlatMeterFee() + statistic.getMeterFee());
                feeStatistic.setTotalFee(feeStatistic.getTotalFee() + statistic.getMeterFee());
            }
            feeStatistic.setUpdated(now);

            feeStatisticsDao.update(feeStatistic);
        }

        return 1;
    }

    @Override
    public String getLastMeter(int flatRenterId) {
        List<FlatMeterPaymentHistory> histories = flatMeterPaymentHistoryDao.getFlatMeterPaymentHistory(flatRenterId);
        if (null != histories && histories.size() > 0) {
            return histories.get(0).getLastRecord();
        }

        return null;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public CurrentPage<FlatMeterPaymentHistoryListVO> listFlatMeterPaymentHistories(int flatId,
            PaginationRequest paginationRequest) {
        int curn = paginationRequest.getCurn() > 0 ? paginationRequest.getCurn() : 1;
        int pageSize = paginationRequest.getPs();
        PaginationCriteria pagination = new PaginationCriteria();
        pagination.setOffset((curn - 1) * pageSize);
        pagination.setLimit(pageSize);

        Pagination<FlatMeterPaymentHistory> flatMeterPaymentHistories = flatMeterPaymentHistoryDao
                .listFlatMeterPaymentHistory(flatId, pagination);

        return new CurrentPage(curn, flatMeterPaymentHistories.getCount(), pageSize,
                formatFlatMeterPaymentHistories(flatMeterPaymentHistories.getItems()));
    }

    private List<FlatMeterPaymentHistoryListVO> formatFlatMeterPaymentHistories(
            List<FlatMeterPaymentHistory> flatMeterPaymentHistories) {
        List<FlatMeterPaymentHistoryListVO> flatMeterPaymentHistoryListVOs = new ArrayList<FlatMeterPaymentHistoryListVO>();
        if (null == flatMeterPaymentHistories || flatMeterPaymentHistories.isEmpty()) {
            return flatMeterPaymentHistoryListVOs;
        }

        for (FlatMeterPaymentHistory history : flatMeterPaymentHistories) {
            FlatMeterPaymentHistoryListVO vo = new FlatMeterPaymentHistoryListVO();
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

            flatMeterPaymentHistoryListVOs.add(vo);
        }

        return flatMeterPaymentHistoryListVOs;
    }

    @Override
    public void generateFlatFeeStatistics(String year, int quarter) {
        Timestamp now = new Timestamp(System.currentTimeMillis());

        List<Integer> flatRenterIds = flatFeeStatisticsDao.findFlatRenterIds(year, quarter);
        for (Integer flatRenterId : flatRenterIds) {
            FlatFeeStatistics statistic = new FlatFeeStatistics();
            statistic.setFlatRenterId(flatRenterId);
            statistic.setYear(year);
            statistic.setQuarter(quarter);
            statistic.setCreated(now);

            flatFeeStatisticsDao.create(statistic);
        }
    }

}
