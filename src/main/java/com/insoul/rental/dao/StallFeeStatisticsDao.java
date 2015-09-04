package com.insoul.rental.dao;

import java.util.List;

import com.insoul.rental.model.StallFeeStatistics;

public interface StallFeeStatisticsDao {
    void create(StallFeeStatistics statistic);

    void update(StallFeeStatistics statistic);

    StallFeeStatistics getStallFeeStatistic(int stallRenterId, String year, int quarter);

    StallFeeStatistics getStallFeeStatistic(int statisticId);

    List<StallFeeStatistics> findStallFeeStatistics(List<Integer> ids, String year, int quarter);

    List<Integer> findStallRenterIds(String year, int quarter);
}
