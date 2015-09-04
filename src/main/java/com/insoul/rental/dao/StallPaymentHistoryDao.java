package com.insoul.rental.dao;

import com.insoul.rental.criteria.PaginationCriteria;
import com.insoul.rental.model.Pagination;
import com.insoul.rental.model.StallPaymentHistory;

public interface StallPaymentHistoryDao {

    void create(StallPaymentHistory stallPaymentHistory);

    Pagination<StallPaymentHistory> listStallPaymentHistory(int stallId, PaginationCriteria pagination);

    StallPaymentHistory getById(int id);
}
