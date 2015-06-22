package com.insoul.rental.dao;

import java.util.List;

import com.insoul.rental.criteria.RenterCriteria;
import com.insoul.rental.model.Pagination;
import com.insoul.rental.model.Renter;

public interface RenterDao extends BaseDao {

    Pagination<Renter> listRenters(RenterCriteria criteria);

    int create(Renter renter);

    void update(Renter renter);

    void delete(int renterId);

    Renter getById(int renterId);

    List<Renter> listRenters();

    int countRenter();
}
