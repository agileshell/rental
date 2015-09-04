package com.insoul.rental.dao;

import com.insoul.rental.criteria.PaginationCriteria;
import com.insoul.rental.model.FlatPaymentHistory;
import com.insoul.rental.model.Pagination;

public interface FlatPaymentHistoryDao {

    void create(FlatPaymentHistory flatPaymentHistory);

    Pagination<FlatPaymentHistory> listFlatPaymentHistory(int stallId, PaginationCriteria pagination);

    FlatPaymentHistory getById(int id);
}
