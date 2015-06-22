package com.insoul.rental.dao;

import com.insoul.rental.criteria.StallCriteria;
import com.insoul.rental.model.Pagination;
import com.insoul.rental.model.Stall;

public interface StallDao {

    Pagination<Stall> listStalls(StallCriteria criteria);

    int create(Stall stall);

    void update(Stall stall);

    void delete(int stallId);

    Stall getById(int stallId);

    void unRent(int stallId);

    int countStall(Boolean hasRent);
}
