package com.insoul.rental.dao;

import java.util.List;

import com.insoul.rental.criteria.PaginationCriteria;
import com.insoul.rental.model.FlatMeterPaymentHistory;
import com.insoul.rental.model.Pagination;

public interface FlatMeterPaymentHistoryDao {

    void create(FlatMeterPaymentHistory flatMeterPaymentHistory);

    List<FlatMeterPaymentHistory> getFlatMeterPaymentHistory(int flatRenterId);

    Pagination<FlatMeterPaymentHistory> listFlatMeterPaymentHistory(int flatId, PaginationCriteria pagination);

    FlatMeterPaymentHistory getById(int id);
}
