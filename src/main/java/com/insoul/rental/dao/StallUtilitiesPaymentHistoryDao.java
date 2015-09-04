package com.insoul.rental.dao;

import java.util.List;

import com.insoul.rental.criteria.PaginationCriteria;
import com.insoul.rental.model.Pagination;
import com.insoul.rental.model.StallUtilitiesPaymentHistory;

public interface StallUtilitiesPaymentHistoryDao {

    void create(StallUtilitiesPaymentHistory stallUtilitiesPaymentHistory);

    List<StallUtilitiesPaymentHistory> getStallUtilitiesPaymentHistory(int stallRenterId, int type);

    Pagination<StallUtilitiesPaymentHistory> listStallUtilitiesPaymentHistory(int stallId, int type,
            PaginationCriteria pagination);

    StallUtilitiesPaymentHistory getById(int id);
}
