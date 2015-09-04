package com.insoul.rental.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.insoul.rental.dao.FlatFeeStatisticsDao;
import com.insoul.rental.model.FlatFeeStatistics;

@Repository
public class FlatFeeStatisticsDaoImpl extends BaseDaoImpl implements FlatFeeStatisticsDao {

    @Override
    public void create(FlatFeeStatistics statistic) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO flat_fee_statistics(flat_renter_id, year, quarter");
        StringBuilder condition = new StringBuilder();
        condition.append(" VALUES(?,?,?");

        List<Object> args = new ArrayList<Object>();

        args.add(statistic.getFlatRenterId());
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
    public void update(FlatFeeStatistics statistic) {
        List<Object> args = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE flat_fee_statistics SET updated = ?");
        args.add(statistic.getUpdated());

        if (0 != statistic.getIsPayRent()) {
            sql.append(", is_pay_rent = ?");
            args.add(statistic.getIsPayRent());
        }
        if (0 != statistic.getIsPayMeter()) {
            sql.append(", is_pay_meter = ?");
            args.add(statistic.getIsPayMeter());
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
        if (statistic.getTotalFee() > 0) {
            sql.append(", total_fee = ?");
            args.add(statistic.getTotalFee());
        }

        sql.append(" WHERE flat_renter_id = ? AND year = ? AND quarter = ?");
        args.add(statistic.getFlatRenterId());
        args.add(statistic.getYear());
        args.add(statistic.getQuarter());

        this.jdbcTemplate.update(sql.toString(), args.toArray());
    }

    @Override
    public FlatFeeStatistics getFlatFeeStatistic(int flatRenterId, String year, int quarter) {
        List<FlatFeeStatistics> list = this.jdbcTemplate.query(
                "SELECT * FROM flat_fee_statistics WHERE flat_renter_id = ? AND year = ? AND quarter = ?",
                new Object[] { flatRenterId, year, quarter }, new FlatFeeStatisticMapper());

        return null != list && list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public FlatFeeStatistics getFlatFeeStatistic(int id) {
        List<FlatFeeStatistics> list = this.jdbcTemplate.query("SELECT * FROM flat_fee_statistics WHERE id = ?",
                new Object[] { id }, new FlatFeeStatisticMapper());

        return null != list && list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public List<FlatFeeStatistics> findFlatFeeStatistics(List<Integer> ids, String year, int quarter) {
        if (null == ids || ids.isEmpty()) {
            return new ArrayList<FlatFeeStatistics>();
        }

        String sql = "SELECT ffs.id, ffs.flat_renter_id, ffs.year, ffs.quarter, ffs.is_pay_rent, ffs.is_pay_meter, ffs.is_pay_all, ffs.created FROM flat_fee_statistics ffs WHERE ffs.year = :year AND ffs.quarter = :quarter AND ffs.flat_renter_id IN(:ids)";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("year", year);
        parameters.addValue("quarter", quarter);
        parameters.addValue("ids", ids);

        List<FlatFeeStatistics> list = this.namedJdbcTemplate.query(sql, parameters,
                new RowMapper<FlatFeeStatistics>() {
                    public FlatFeeStatistics mapRow(ResultSet rs, int rowNumber) throws SQLException {
                        FlatFeeStatistics ss = new FlatFeeStatistics();
                        ss.setId(rs.getInt("id"));
                        ss.setFlatRenterId(rs.getInt("flat_renter_id"));
                        ss.setYear(rs.getString("year"));
                        ss.setQuarter(rs.getInt("quarter"));

                        ss.setIsPayRent(rs.getInt("is_pay_rent"));
                        ss.setIsPayMeter(rs.getInt("is_pay_meter"));
                        ss.setIsPayAll(rs.getInt("is_pay_all"));
                        ss.setCreated(rs.getTimestamp("created"));

                        return ss;
                    }
                });

        return list;
    }

    private static final class FlatFeeStatisticMapper implements RowMapper<FlatFeeStatistics> {
        public FlatFeeStatistics mapRow(ResultSet rs, int rowNum) throws SQLException {
            FlatFeeStatistics statistic = new FlatFeeStatistics();

            statistic.setId(rs.getInt("id"));
            statistic.setFlatRenterId(rs.getInt("flat_renter_id"));
            statistic.setYear(rs.getString("year"));
            statistic.setQuarter(rs.getInt("quarter"));

            statistic.setIsPayRent(rs.getInt("is_pay_rent"));
            statistic.setIsPayMeter(rs.getInt("is_pay_meter"));
            statistic.setIsPayAll(rs.getInt("is_pay_all"));

            statistic.setRentFee(rs.getFloat("rent_fee"));
            statistic.setMeterFee(rs.getFloat("meter_fee"));
            statistic.setTotalFee(rs.getFloat("total_fee"));

            statistic.setCreated(rs.getTimestamp("created"));

            return statistic;
        }
    }

    @Override
    public List<Integer> findFlatRenterIds(String year, int quarter) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT f.flat_renter_id FROM flat f WHERE f.is_deleted = 0 AND f.flat_renter_id IS NOT NULL")
                .append(" AND f.flat_renter_id NOT IN( ")
                .append(" SELECT ffs.flat_renter_id FROM flat_fee_statistics ffs WHERE ffs.year = ? AND ffs.quarter = ?")
                .append(" )");

        List<Integer> flatRenterIds = this.jdbcTemplate.query(sql.toString(), new Object[] { year, quarter },
                new RowMapper<Integer>() {
                    public Integer mapRow(ResultSet rs, int rowNumber) throws SQLException {
                        return rs.getInt("flat_renter_id");
                    }
                });

        return flatRenterIds;
    }
}
