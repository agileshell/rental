package com.insoul.rental.dao;

import java.util.List;

import com.insoul.rental.criteria.PaginationCriteria;
import com.insoul.rental.model.Pagination;
import com.insoul.rental.model.StallUtilitiesPaymentHistory;
import com.insoul.rental.model.StallUtilitiesPaymentStatus;

public interface StallUtilitiesPaymentHistoryDao {

    void create(StallUtilitiesPaymentHistory stallUtilitiesPaymentHistory);

    List<StallUtilitiesPaymentStatus> getStallPaymentStatus(List<Integer> ids, int quarter);

    List<StallUtilitiesPaymentHistory> getStallUtilitiesPaymentHistory(int stallRenterId, int type);

    Pagination<StallUtilitiesPaymentHistory> listStallUtilitiesPaymentHistory(int stallId, int type,
            PaginationCriteria pagination);

    StallUtilitiesPaymentHistory getById(int id);
}
