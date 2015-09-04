package com.insoul.rental.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.insoul.rental.criteria.StallCriteria;
import com.insoul.rental.criteria.StallStatisticCriteria;
import com.insoul.rental.dao.StallDao;
import com.insoul.rental.model.Pagination;
import com.insoul.rental.model.Stall;
import com.insoul.rental.model.StallStatistic;

@Repository
public class StallDaoImpl extends BaseDaoImpl implements StallDao {

    @SuppressWarnings({ "unchecked", "deprecation", "rawtypes" })
    public Pagination<Stall> listStalls(StallCriteria criteria) {
        List<Object> args = new ArrayList<Object>();

        StringBuilder sqlCountRows = new StringBuilder();
        sqlCountRows.append("SELECT count(*) FROM stall s WHERE s.is_deleted = 0");

        StringBuilder sqlFetchRows = new StringBuilder();
        sqlFetchRows
                .append("SELECT s.*, r.name AS renter_name FROM stall s LEFT JOIN renter r ON s.renter_id = r.renter_id WHERE s.is_deleted = 0");

        StringBuilder condition = new StringBuilder();
        if (null != criteria.getIsRented()) {
            if (criteria.getIsRented()) {
                condition.append(" AND s.renter_id IS NOT NULL");
            } else {
                condition.append(" AND s.renter_id IS NULL");
            }
        }
        if (null != criteria.getSubareaId()) {
            condition.append(" AND s.subarea_id = ?");
            args.add(criteria.getSubareaId());
        }
        if (null != criteria.getRenterId()) {
            condition.append(" AND s.renter_id = ?");
            args.add(criteria.getRenterId());
        }
        if (StringUtils.isNotEmpty(criteria.getName())) {
            condition.append(" AND s.name LIKE ?");
            args.add("%" + criteria.getName() + "%");
        }

        int count = this.jdbcTemplate.queryForInt(sqlCountRows.append(condition).toString(), args.toArray());

        if (0 != criteria.getLimit()) {
            condition.append(" LIMIT ?, ?");
            args.add(criteria.getOffset());
            args.add(criteria.getLimit());
        }
        List<Stall> list = this.jdbcTemplate.query(sqlFetchRows.append(condition).toString(), args.toArray(),
                new StallMapper());

        return new Pagination(count, list);
    }

    public int create(final Stall stall) {
        final String sql = "INSERT INTO stall(subarea_id, name, month_price, comment, created, updated) VALUES(?,?,?,?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setObject(1, stall.getSubareaId());
                ps.setObject(2, stall.getName());
                ps.setObject(3, stall.getMonthPrice());
                ps.setObject(4, stall.getComment());
                ps.setObject(5, stall.getCreated());
                ps.setObject(6, stall.getUpdated());

                return ps;
            }
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    public void update(Stall stall) {
        List<Object> args = new ArrayList<Object>();

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE stall SET subarea_id = ?, name = ?, month_price = ?, comment = ?, updated = ?");
        args.add(stall.getSubareaId());
        args.add(stall.getName());
        args.add(stall.getMonthPrice());
        args.add(stall.getComment());
        args.add(stall.getUpdated());

        if (0 != stall.getRenterId()) {
            sql.append(", renter_id = ?");
            args.add(stall.getRenterId());
        } else {
            sql.append(", renter_id = NULL");
        }
        if (0 != stall.getStallRenterId()) {
            sql.append(", stall_renter_id = ?");
            args.add(stall.getStallRenterId());
        } else {
            sql.append(", stall_renter_id = NULL");
        }

        sql.append(" WHERE stall_id = ?");
        args.add(stall.getStallId());

        this.jdbcTemplate.update(sql.toString(), args.toArray());
    }

    public void delete(int stallId) {
        String sql = "UPDATE stall SET is_deleted = 1, updated = ? WHERE stall_id = ?";

        this.jdbcTemplate.update(sql, new Object[] { new Timestamp(System.currentTimeMillis()), stallId });
    }

    public Stall getById(int stallId) {
        List<Stall> list = this.jdbcTemplate
                .query("SELECT s.*, r.name AS renter_name FROM stall s LEFT JOIN renter r ON s.renter_id = r.renter_id WHERE s.stall_id = ?",
                        new Object[] { stallId }, new StallMapper());

        return null != list && list.size() > 0 ? list.get(0) : null;
    }

    public void unRent(int stallId) {
        String sql = "UPDATE stall SET renter_id = NULL, stall_renter_id = NULL, updated = ? WHERE stall_id = ?";

        this.jdbcTemplate.update(sql, new Object[] { new Timestamp(System.currentTimeMillis()), stallId });
    }

    private static final class StallMapper implements RowMapper<Stall> {
        public Stall mapRow(ResultSet rs, int rowNum) throws SQLException {
            Stall stall = new Stall();
            stall.setStallId(rs.getInt("stall_id"));
            stall.setSubareaId(rs.getInt("subarea_id"));
            stall.setName(rs.getString("name"));
            stall.setMonthPrice(rs.getInt("month_price"));
            stall.setRenterId(rs.getInt("renter_id"));
            stall.setStallRenterId(rs.getInt("stall_renter_id"));
            stall.setComment(rs.getString("comment"));
            stall.setCreated(rs.getTimestamp("created"));
            stall.setUpdated(rs.getTimestamp("updated"));
            stall.setRenterName(rs.getString("renter_name"));

            return stall;
        }
    }

    @Override
    public int countStall(Boolean hasRent) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT count(*) FROM stall WHERE is_deleted = 0");

        StringBuilder condition = new StringBuilder();
        if (null != hasRent) {
            if (hasRent) {
                condition.append(" AND renter_id IS NOT NULL");
            } else {
                condition.append(" AND renter_id IS NULL");
            }
        }

        @SuppressWarnings("deprecation")
        int count = this.jdbcTemplate.queryForInt(sql.append(condition).toString(), new Object[] {});

        return count;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Pagination<StallStatistic> flatStatistic(StallStatisticCriteria criteria) {
        List<Object> args = new ArrayList<Object>();

        StringBuilder sqlCountRows = new StringBuilder();
        sqlCountRows.append("SELECT count(*) FROM stall s LEFT JOIN renter r ON s.renter_id = r.renter_id")
                .append(" LEFT JOIN stall_fee_statistics sfs ON sfs.stall_renter_id = s.stall_renter_id")
                .append(" WHERE s.is_deleted = 0 AND s.stall_renter_id IS NOT NULL");

        StringBuilder sqlFetchRows = new StringBuilder();
        sqlFetchRows
                .append("SELECT s.stall_id, s.name AS stall_name, r.renter_id, r.name AS renter_name")
                .append(", sfs.id, sfs.year, sfs.quarter, sfs.is_pay_rent, sfs.is_pay_meter, sfs.is_pay_water, sfs.is_pay_all, sfs.rent_fee, sfs.meter_fee, sfs.water_fee, sfs.total_fee")
                .append(" FROM stall s LEFT JOIN renter r ON s.renter_id = r.renter_id")
                .append(" LEFT JOIN stall_fee_statistics sfs ON sfs.stall_renter_id = s.stall_renter_id")
                .append(" WHERE s.is_deleted = 0 AND s.stall_renter_id IS NOT NULL");

        StringBuilder condition = new StringBuilder();
        if (criteria.getType().equals("all")) {
            if (criteria.isPaid()) {
                condition.append(" AND sfs.is_pay_all = 2");
            } else {
                condition.append(" AND sfs.is_pay_all != 2");
            }
        } else if (criteria.getType().equals("rent")) {
            if (criteria.isPaid()) {
                condition.append(" AND sfs.is_pay_rent = 2");
            } else {
                condition.append(" AND sfs.is_pay_rent != 2");
            }
        } else if (criteria.getType().equals("meter")) {
            if (criteria.isPaid()) {
                condition.append(" AND sfs.is_pay_meter = 2");
            } else {
                condition.append(" AND sfs.is_pay_meter != 2");
            }
        } else if (criteria.getType().equals("water")) {
            if (criteria.isPaid()) {
                condition.append(" AND sfs.is_pay_water = 2");
            } else {
                condition.append(" AND sfs.is_pay_water != 2");
            }
        }

        if (StringUtils.isNotEmpty(criteria.getYear())) {
            condition.append(" AND sfs.year = ?");
            args.add(criteria.getYear());
        }
        if (null != criteria.getQuarter() && 0 != criteria.getQuarter()) {
            condition.append(" AND sfs.quarter = ?");
            args.add(criteria.getQuarter());
        }
        if (StringUtils.isNotEmpty(criteria.getStallName())) {
            condition.append(" AND s.name LIKE ?");
            args.add("%" + criteria.getStallName() + "%");
        }
        if (StringUtils.isNotEmpty(criteria.getRentor())) {
            condition.append(" AND r.name LIKE ?");
            args.add("%" + criteria.getRentor() + "%");
        }

        @SuppressWarnings("deprecation")
        int count = this.jdbcTemplate.queryForInt(sqlCountRows.append(condition).toString(), args.toArray());

        if (0 != criteria.getLimit()) {
            condition.append(" LIMIT ?, ?");
            args.add(criteria.getOffset());
            args.add(criteria.getLimit());
        }
        List<StallStatistic> list = this.jdbcTemplate.query(sqlFetchRows.append(condition).toString(), args.toArray(),
                new RowMapper<StallStatistic>() {
                    public StallStatistic mapRow(ResultSet rs, int rowNumber) throws SQLException {
                        StallStatistic statistic = new StallStatistic();
                        statistic.setYear(rs.getString("year"));
                        statistic.setQuarter(rs.getInt("quarter"));

                        statistic.setStallId(rs.getInt("stall_id"));
                        statistic.setStallName(rs.getString("stall_name"));
                        statistic.setRenterId(rs.getInt("renter_id"));
                        statistic.setRenterName(rs.getString("renter_name"));

                        statistic.setStatisticId(rs.getInt("id"));
                        statistic.setIsPayRent(rs.getInt("is_pay_rent"));
                        statistic.setIsPayMeter(rs.getInt("is_pay_meter"));
                        statistic.setIsPayWater(rs.getInt("is_pay_water"));
                        statistic.setIsPayAll(rs.getInt("is_pay_all"));
                        statistic.setRentFee(rs.getFloat("rent_fee"));
                        statistic.setMeterFee(rs.getFloat("meter_fee"));
                        statistic.setWaterFee(rs.getFloat("water_fee"));
                        statistic.setTotalFee(rs.getFloat("total_fee"));

                        return statistic;
                    }
                });

        return new Pagination(count, list);
    }

}
