package com.insoul.rental.dao;

import com.insoul.rental.criteria.FlatCriteria;
import com.insoul.rental.criteria.FlatStatisticCriteria;
import com.insoul.rental.model.Flat;
import com.insoul.rental.model.FlatStatistic;
import com.insoul.rental.model.Pagination;

public interface FlatDao {

    Pagination<Flat> listFlats(FlatCriteria criteria);

    int create(Flat flat);

    void update(Flat flat);

    void delete(int flatId);

    Flat getById(int flatId);

    void unRent(int flatId);

    int countFlat(Boolean hasRent);

    Pagination<FlatStatistic> flatStatistic(FlatStatisticCriteria criteria);
}
