package com.insoul.rental.dao;

import java.util.List;

import com.insoul.rental.criteria.PaginationCriteria;
import com.insoul.rental.model.FlatPaymentHistory;
import com.insoul.rental.model.FlatPaymentStatus;
import com.insoul.rental.model.Pagination;

public interface FlatPaymentHistoryDao {

    void create(FlatPaymentHistory flatPaymentHistory);

    List<FlatPaymentStatus> getFlatPaymentStatus(List<Integer> ids, int quarter);

    Pagination<FlatPaymentHistory> listFlatPaymentHistory(int stallId, PaginationCriteria pagination);

    FlatPaymentHistory getById(int id);
}
