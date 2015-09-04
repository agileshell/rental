package com.insoul.rental.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.insoul.rental.dao.StallFeeStatisticsDao;
import com.insoul.rental.model.StallFeeStatistics;

@Repository
public class StallFeeStatisticsDaoImpl extends BaseDaoImpl implements StallFeeStatisticsDao {

    @Override
    public void create(StallFeeStatistics statistic) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO stall_fee_statistics(stall_renter_id, year, quarter");
        StringBuilder condition = new StringBuilder();
        condition.append(" VALUES(?,?,?");

        List<Object> args = new ArrayList<Object>();

        args.add(statistic.getStallRenterId());
        args.add(statistic.getYear());
        args.add(statistic.getQuarter());

        if (0 != statistic.getIsPayRent()) {
            sql.append(", is_pay_rent");
            condition.append(",?");
            args.add(statistic.getIsPayRent());
        }
        if (0 != statistic.getIsPayMeter()) {
            sql.append(", is_pay_meter");
            condition.append(",?");
            args.add(statistic.getIsPayMeter());
        }
        if (0 != statistic.getIsPayWater()) {
            sql.append(", is_pay_water");
            condition.append(",?");
            args.add(statistic.getIsPayWater());
        }
        if (0 != statistic.getIsPayAll()) {
            sql.append(", is_pay_all");
            condition.append(",?");
            args.add(statistic.getIsPayAll());
        }

        if (statistic.getRentFee() > 0) {
            sql.append(", rent_fee");
            condition.append(",?");
            args.add(statistic.getRentFee());
        }
        if (statistic.getMeterFee() > 0) {
            sql.append(", meter_fee");
            condition.append(",?");
            args.add(statistic.getMeterFee());
        }
        if (statistic.getWaterFee() > 0) {
            sql.append(", water_fee");
            condition.append(",?");
            args.add(statistic.getWaterFee());
        }
        if (statistic.getTotalFee() > 0) {
            sql.append(", total_fee");
            condition.append(",?");
            args.add(statistic.getTotalFee());
        }

        sql.append(", created)");
        condition.append(",?)");
        args.add(statistic.getCreated());

        this.jdbcTemplate.update(sql.append(condition).toString(), args.toArray());
    }

    @Override
    public void update(StallFeeStatistics statistic) {
        List<Object> args = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE stall_fee_statistics SET updated = ?");
        args.add(statistic.getUpdated());

        if (0 != statistic.getIsPayRent()) {
            sql.append(", is_pay_rent = ?");
            args.add(statistic.getIsPayRent());
        }
        if (0 != statistic.getIsPayMeter()) {
            sql.append(", is_pay_meter = ?");
            args.add(statistic.getIsPayMeter());
        }
        if (0 != statistic.getIsPayWater()) {
            sql.append(", is_pay_water = ?");
            args.add(statistic.getIsPayWater());
        }
        if (0 != statistic.getIsPayAll()) {
            sql.append(", is_pay_all = ?");
            args.add(statistic.getIsPayAll());
        }

        if (statistic.getRentFee() > 0) {
            sql.append(", rent_fee = ?");
            args.add(statistic.getRentFee());
        }
        if (statistic.getMeterFee() > 0) {
            sql.append(", meter_fee = ?");
            args.add(statistic.getMeterFee());
        }
        if (statistic.getWaterFee() > 0) {
            sql.append(", water_fee = ?");
            args.add(statistic.getWaterFee());
        }
        if (statistic.getTotalFee() > 0) {
            sql.append(", total_fee = ?");
            args.add(statistic.getTotalFee());
        }

        sql.append(" WHERE stall_renter_id = ? AND year = ? AND quarter = ?");
        args.add(statistic.getStallRenterId());
        args.add(statistic.getYear());
        args.add(statistic.getQuarter());

        this.jdbcTemplate.update(sql.toString(), args.toArray());
    }

    @Override
    public StallFeeStatistics getStallFeeStatistic(int stallRenterId, String year, int quarter) {
        List<StallFeeStatistics> list = this.jdbcTemplate.query(
                "SELECT * FROM stall_fee_statistics WHERE stall_renter_id = ? AND year = ? AND quarter = ?",
                new Object[] { stallRenterId, year, quarter }, new StallFeeStatisticMapper());

        return null != list && list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public StallFeeStatistics getStallFeeStatistic(int statisticId) {
        List<StallFeeStatistics> list = this.jdbcTemplate.query("SELECT * FROM stall_fee_statistics WHERE id = ?",
                new Object[] { statisticId }, new StallFeeStatisticMapper());

        return null != list && list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public List<StallFeeStatistics> findStallFeeStatistics(List<Integer> ids, String year, int quarter) {
        if (null == ids || ids.isEmpty()) {
            return new ArrayList<StallFeeStatistics>();
        }

        String sql = "SELECT sfs.id, sfs.stall_renter_id, sfs.year, sfs.quarter, sfs.is_pay_rent, sfs.is_pay_meter, sfs.is_pay_water, sfs.is_pay_all, sfs.created FROM stall_fee_statistics sfs WHERE sfs.year = :year AND sfs.quarter = :quarter AND sfs.stall_renter_id IN(:ids)";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("year", year);
        parameters.addValue("quarter", quarter);
        parameters.addValue("ids", ids);

        List<StallFeeStatistics> list = this.namedJdbcTemplate.query(sql, parameters,
                new RowMapper<StallFeeStatistics>() {
                    public StallFeeStatistics mapRow(ResultSet rs, int rowNumber) throws SQLException {
                        StallFeeStatistics statistic = new StallFeeStatistics();
                        statistic.setId(rs.getInt("id"));
                        statistic.setStallRenterId(rs.getInt("stall_renter_id"));
                        statistic.setYear(rs.getString("year"));
                        statistic.setQuarter(rs.getInt("quarter"));

                        statistic.setIsPayRent(rs.getInt("is_pay_rent"));
                        statistic.setIsPayMeter(rs.getInt("is_pay_meter"));
                        statistic.setIsPayWater(rs.getInt("is_pay_water"));
                        statistic.setIsPayAll(rs.getInt("is_pay_all"));
                        statistic.setCreated(rs.getTimestamp("created"));

                        return statistic;
                    }
                });

        return list;
    }

    private static final class StallFeeStatisticMapper implements RowMapper<StallFeeStatistics> {
        public StallFeeStatistics mapRow(ResultSet rs, int rowNum) throws SQLException {
            StallFeeStatistics statistic = new StallFeeStatistics();

            statistic.setId(rs.getInt("id"));
            statistic.setStallRenterId(rs.getInt("stall_renter_id"));
            statistic.setYear(rs.getString("year"));
            statistic.setQuarter(rs.getInt("quarter"));

            statistic.setIsPayRent(rs.getInt("is_pay_rent"));
            statistic.setIsPayMeter(rs.getInt("is_pay_meter"));
            statistic.setIsPayWater(rs.getInt("is_pay_water"));
            statistic.setIsPayAll(rs.getInt("is_pay_all"));

            statistic.setRentFee(rs.getFloat("rent_fee"));
            statistic.setMeterFee(rs.getFloat("meter_fee"));
            statistic.setWaterFee(rs.getFloat("water_fee"));
            statistic.setTotalFee(rs.getFloat("total_fee"));

            statistic.setCreated(rs.getTimestamp("created"));

            return statistic;
        }
    }

    @Override
    public List<Integer> findStallRenterIds(String year, int quarter) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT s.stall_renter_id FROM stall s WHERE s.is_deleted = 0 AND s.stall_renter_id IS NOT NULL")
                .append(" AND s.stall_renter_id NOT IN( ")
                .append(" SELECT sfs.stall_renter_id FROM stall_fee_statistics sfs WHERE sfs.year = ? AND sfs.quarter = ?")
                .append(" )");

        List<Integer> stallRenterIds = this.jdbcTemplate.query(sql.toString(), new Object[] { year, quarter },
                new RowMapper<Integer>() {
                    public Integer mapRow(ResultSet rs, int rowNumber) throws SQLException {
                        return rs.getInt("stall_renter_id");
                    }
                });

        return stallRenterIds;
    }
}
