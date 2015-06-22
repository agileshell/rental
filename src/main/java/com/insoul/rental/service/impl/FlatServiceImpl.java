package com.insoul.rental.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.insoul.rental.criteria.FlatCriteria;
import com.insoul.rental.criteria.PaginationCriteria;
import com.insoul.rental.model.Flat;
import com.insoul.rental.model.FlatMeterPaymentHistory;
import com.insoul.rental.model.FlatMeterPaymentStatus;
import com.insoul.rental.model.FlatPaymentHistory;
import com.insoul.rental.model.FlatPaymentStatus;
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

        Map<Integer, FlatListVO> flatIdFlatMap = new HashMap<Integer, FlatListVO>();
        List<Integer> flatIds = new ArrayList<Integer>();

        for (Flat flat : flats) {
            int flatId = flat.getFlatId();

            FlatListVO vo = new FlatListVO();
            vo.setFlatId(flatId);
            vo.setName(flat.getName());
            vo.setMonthPrice(flat.getMonthPrice());
            vo.setRenterId(flat.getRenterId());
            vo.setRenterName(flat.getRenterName());
            vo.setCreated(flat.getCreated());

            flatVOs.add(vo);

            flatIdFlatMap.put(flatId, vo);

            flatIds.add(flatId);
        }

        int quarter = getQuarter();

        List<FlatPaymentStatus> flatPaymentStatuses = flatPaymentHistoryDao.getFlatPaymentStatus(flatIds, quarter);
        for (FlatPaymentStatus flatPaymentStatus : flatPaymentStatuses) {
            if (flatIdFlatMap.containsKey(flatPaymentStatus.getFlatId())) {
                FlatListVO flatListVO = flatIdFlatMap.get(flatPaymentStatus.getFlatId());
                flatListVO.setHasPaidRent(flatPaymentStatus.isHasPaidRent());
            }
        }

        List<FlatMeterPaymentStatus> flatMeterPaymentStatuses = flatMeterPaymentHistoryDao.getFlatPaymentStatus(
                flatIds, quarter);
        for (FlatMeterPaymentStatus flatMeterPaymentStatus : flatMeterPaymentStatuses) {
            if (flatIdFlatMap.containsKey(flatMeterPaymentStatus.getFlatId())) {
                FlatListVO flatListVO = flatIdFlatMap.get(flatMeterPaymentStatus.getFlatId());
                flatListVO.setHasPaidMeter(flatMeterPaymentStatus.isHasPaidRent());
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

        FlatPaymentHistory flatPaymentHistory = new FlatPaymentHistory();
        flatPaymentHistory.setFlatRenterId(flat.getFlatRenterId());
        flatPaymentHistory.setStartDate(rentPayment.getStartDate());
        flatPaymentHistory.setEndDate(rentPayment.getEndDate());
        flatPaymentHistory.setTotalPrice(rentPayment.getTotalPrice());
        flatPaymentHistory.setComment(rentPayment.getComment());
        flatPaymentHistory.setCreated(new Timestamp(System.currentTimeMillis()));

        flatPaymentHistory.setQuarter(getQuarter());

        flatPaymentHistoryDao.create(flatPaymentHistory);
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

        FlatMeterPaymentHistory flatMeterPaymentHistory = new FlatMeterPaymentHistory();
        flatMeterPaymentHistory.setFlatRenterId(flat.getFlatRenterId());
        flatMeterPaymentHistory.setFirstRecord(utilitiesPayment.getFirstRecord());
        flatMeterPaymentHistory.setLastRecord(utilitiesPayment.getLastRecord());
        flatMeterPaymentHistory.setPrice(utilitiesPayment.getPrice());
        flatMeterPaymentHistory.setTotalPrice(utilitiesPayment.getTotalPrice());
        flatMeterPaymentHistory.setComment(utilitiesPayment.getComment());
        flatMeterPaymentHistory.setRecordDate(utilitiesPayment.getRecordDate());
        flatMeterPaymentHistory.setCreated(new Timestamp(System.currentTimeMillis()));

        flatMeterPaymentHistory.setQuarter(getQuarter());

        flatMeterPaymentHistoryDao.create(flatMeterPaymentHistory);
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

}
