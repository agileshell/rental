package com.insoul.rental.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.insoul.rental.criteria.PaginationCriteria;
import com.insoul.rental.dao.FeeStatisticsDao;
import com.insoul.rental.model.FeeStatistics;
import com.insoul.rental.model.Pagination;

@Repository
public class FeeStatisticsDaoImpl extends BaseDaoImpl implements FeeStatisticsDao {

    @Override
    public void create(FeeStatistics statistic) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO fee_statistics(record_date, year, month, quarter, stall_rent_fee, stall_meter_fee, stall_water_fee, flat_rent_fee, flat_meter_fee, total_fee, created) VALUES(?,?,?,?,?,?,?,?,?,?,?)");

        List<Object> args = new ArrayList<Object>();
        args.add(statistic.getRecordDate());
        args.add(statistic.getYear());
        args.add(statistic.getMonth());
        args.add(statistic.getQuarter());

        args.add(statistic.getStallRentFee());
        args.add(statistic.getStallMeterFee());
        args.add(statistic.getStallWaterFee());
        args.add(statistic.getFlatRentFee());
        args.add(statistic.getFlatMeterFee());
        args.add(statistic.getTotalFee());
        args.add(statistic.getCreated());

        this.jdbcTemplate.update(sql.toString(), args.toArray());
    }

    @Override
    public void update(FeeStatistics statistic) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE fee_statistics SET stall_rent_fee=?, stall_meter_fee=?, stall_water_fee=?, flat_rent_fee=?, flat_meter_fee=?, total_fee=?, created=? WHERE record_date = ?");

        List<Object> args = new ArrayList<Object>();
        args.add(statistic.getStallRentFee());
        args.add(statistic.getStallMeterFee());
        args.add(statistic.getStallWaterFee());
        args.add(statistic.getFlatRentFee());
        args.add(statistic.getFlatMeterFee());
        args.add(statistic.getTotalFee());
        args.add(statistic.getCreated());

        args.add(statistic.getRecordDate());

        this.jdbcTemplate.update(sql.toString(), args.toArray());
    }

    @Override
    public FeeStatistics getFeeStatistic(String recordDate) {
        List<FeeStatistics> list = this.jdbcTemplate.query("SELECT * FROM fee_statistics WHERE record_date = ?",
                new Object[] { recordDate }, new FeeStatisticMapper());

        return null != list && list.size() > 0 ? list.get(0) : null;
    }

    private static final class FeeStatisticMapper implements RowMapper<FeeStatistics> {
        public FeeStatistics mapRow(ResultSet rs, int rowNum) throws SQLException {
            FeeStatistics statistic = new FeeStatistics();

            statistic.setRecordDate(rs.getString("record_date"));
            statistic.setYear(rs.getString("year"));
            statistic.setMonth(rs.getString("month"));
            statistic.setQuarter(rs.getInt("quarter"));

            statistic.setStallRentFee(rs.getFloat("stall_rent_fee"));
            statistic.setStallMeterFee(rs.getFloat("stall_meter_fee"));
            statistic.setStallWaterFee(rs.getFloat("stall_water_fee"));
            statistic.setFlatRentFee(rs.getFloat("flat_rent_fee"));
            statistic.setFlatMeterFee(rs.getFloat("flat_meter_fee"));
            statistic.setTotalFee(rs.getFloat("total_fee"));

            statistic.setCreated(rs.getTimestamp("created"));

            return statistic;
        }
    }

    @Override
    public List<FeeStatistics> annualStatistics() {
        List<FeeStatistics> list = this.jdbcTemplate
                .query("SELECT year, SUM(stall_rent_fee) AS stall_rent_fee, SUM(stall_meter_fee) AS stall_meter_fee, SUM(stall_water_fee) AS stall_water_fee, SUM(flat_rent_fee) AS flat_rent_fee, SUM(flat_meter_fee) AS flat_meter_fee, SUM(total_fee) AS total_fee FROM fee_statistics GROUP BY year ORDER BY year DESC",
                        new Object[] {}, new RowMapper<FeeStatistics>() {
                            public FeeStatistics mapRow(ResultSet rs, int rowNumber) throws SQLException {
                                FeeStatistics statistic = new FeeStatistics();
                                statistic.setYear(rs.getString("year"));
                                statistic.setStallRentFee(rs.getFloat("stall_rent_fee"));
                                statistic.setStallMeterFee(rs.getFloat("stall_meter_fee"));
                                statistic.setStallWaterFee(rs.getFloat("stall_water_fee"));
                                statistic.setFlatRentFee(rs.getFloat("flat_rent_fee"));
                                statistic.setFlatMeterFee(rs.getFloat("flat_meter_fee"));
                                statistic.setTotalFee(rs.getFloat("total_fee"));

                                return statistic;
                            }
                        });

        return list;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public Pagination<FeeStatistics> quarterStatistics(PaginationCriteria criteria) {
        List<Object> args = new ArrayList<Object>();

        StringBuilder sqlCountRows = new StringBuilder();
        sqlCountRows.append("SELECT count(*) AS count FROM fee_statistics GROUP BY year, quarter");

        List<Integer> counts = this.jdbcTemplate.query(sqlCountRows.toString(), args.toArray(),
                new RowMapper<Integer>() {
                    public Integer mapRow(ResultSet rs, int rowNumber) throws SQLException {
                        return rs.getInt("count");
                    }
                });
        int count = null != counts && counts.size() > 0 ? counts.get(0) : 0;

        StringBuilder sqlFetchRows = new StringBuilder();
        sqlFetchRows
                .append("SELECT year, quarter, SUM(stall_rent_fee) AS stall_rent_fee, SUM(stall_meter_fee) AS stall_meter_fee, SUM(stall_water_fee) AS stall_water_fee, SUM(flat_rent_fee) AS flat_rent_fee, SUM(flat_meter_fee) AS flat_meter_fee, SUM(total_fee) AS total_fee FROM fee_statistics GROUP BY year, quarter ORDER BY record_date DESC");

        StringBuilder condition = new StringBuilder();
        if (0 != criteria.getLimit()) {
            condition.append(" LIMIT ?, ?");
            args.add(criteria.getOffset());
            args.add(criteria.getLimit());
        }
        List<FeeStatistics> list = this.jdbcTemplate.query(sqlFetchRows.append(condition).toString(), args.toArray(),
                new RowMapper<FeeStatistics>() {
                    public FeeStatistics mapRow(ResultSet rs, int rowNumber) throws SQLException {
                        FeeStatistics statistic = new FeeStatistics();
                        statistic.setYear(rs.getString("year"));
                        statistic.setQuarter(rs.getInt("quarter"));

                        statistic.setStallRentFee(rs.getFloat("stall_rent_fee"));
                        statistic.setStallMeterFee(rs.getFloat("stall_meter_fee"));
                        statistic.setStallWaterFee(rs.getFloat("stall_water_fee"));
                        statistic.setFlatRentFee(rs.getFloat("flat_rent_fee"));
                        statistic.setFlatMeterFee(rs.getFloat("flat_meter_fee"));
                        statistic.setTotalFee(rs.getFloat("total_fee"));

                        return statistic;
                    }
                });

        return new Pagination(count, list);
    }

}
