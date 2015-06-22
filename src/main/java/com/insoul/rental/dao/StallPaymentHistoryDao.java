package com.insoul.rental.dao;

import java.util.List;

import com.insoul.rental.criteria.PaginationCriteria;
import com.insoul.rental.model.Pagination;
import com.insoul.rental.model.StallPaymentHistory;
import com.insoul.rental.model.StallPaymentStatus;

public interface StallPaymentHistoryDao {

    void create(StallPaymentHistory stallPaymentHistory);

    List<StallPaymentStatus> getStallPaymentStatus(List<Integer> ids, int quarter);

    Pagination<StallPaymentHistory> listStallPaymentHistory(int stallId, PaginationCriteria pagination);

    StallPaymentHistory getById(int id);
}
