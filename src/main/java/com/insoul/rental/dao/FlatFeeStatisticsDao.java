package com.insoul.rental.dao;

import java.util.List;

import com.insoul.rental.model.FlatFeeStatistics;

public interface FlatFeeStatisticsDao {
    void create(FlatFeeStatistics statistic);

    void update(FlatFeeStatistics statistic);

    FlatFeeStatistics getFlatFeeStatistic(int flatRenterId, String year, int quarter);

    FlatFeeStatistics getFlatFeeStatistic(int id);

    List<FlatFeeStatistics> findFlatFeeStatistics(List<Integer> ids, String year, int quarter);

    List<Integer> findFlatRenterIds(String year, int quarter);
}
