package com.insoul.rental.dao;

import java.util.List;

import com.insoul.rental.criteria.PaginationCriteria;
import com.insoul.rental.model.FeeStatistics;
import com.insoul.rental.model.Pagination;

public interface FeeStatisticsDao {
    void create(FeeStatistics statistic);

    void update(FeeStatistics statistic);

    FeeStatistics getFeeStatistic(String recordDate);

    List<FeeStatistics> annualStatistics();

    Pagination<FeeStatistics> quarterStatistics(PaginationCriteria criteria);
}
