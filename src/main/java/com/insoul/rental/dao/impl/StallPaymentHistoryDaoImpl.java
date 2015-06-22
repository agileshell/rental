package com.insoul.rental.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.insoul.rental.criteria.PaginationCriteria;
import com.insoul.rental.dao.StallPaymentHistoryDao;
import com.insoul.rental.model.Pagination;
import com.insoul.rental.model.StallPaymentHistory;
import com.insoul.rental.model.StallPaymentStatus;

@Repository
public class StallPaymentHistoryDaoImpl extends BaseDaoImpl implements StallPaymentHistoryDao {

    public void create(StallPaymentHistory stallPaymentHistory) {
        String sql = "INSERT INTO stall_payment_history(stall_renter_id, start_date, end_date, quarter, total_price, comment, created) VALUES(?,?,?,?,?,?,?)";

        List<Object> args = new ArrayList<Object>();

        args.add(stallPaymentHistory.getStallRenterId());
        args.add(stallPaymentHistory.getStartDate());
        args.add(stallPaymentHistory.getEndDate());
        args.add(stallPaymentHistory.getQuarter());
        args.add(stallPaymentHistory.getTotalPrice());
        args.add(stallPaymentHistory.getComment());
        args.add(stallPaymentHistory.getCreated());

        this.jdbcTemplate.update(sql.toString(), args.toArray());
    }

    public List<StallPaymentStatus> getStallPaymentStatus(List<Integer> ids, int quarter) {
        if (null == ids || ids.isEmpty()) {
            return new ArrayList<StallPaymentStatus>();
        }

        String sql = "SELECT s.stall_id, sph.stall_renter_id FROM stall s LEFT JOIN stall_payment_history sph ON s.stall_renter_id = sph.stall_renter_id WHERE sph.quarter = :quarter AND s.stall_id IN(:ids) GROUP BY s.stall_id";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("quarter", quarter);
        parameters.addValue("ids", ids);
        List<StallPaymentStatus> list = this.namedJdbcTemplate.query(sql, parameters, new StallPaymentStatusMapper());

        return list;
    }

    private static final class StallPaymentStatusMapper implements RowMapper<StallPaymentStatus> {
        public StallPaymentStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
            StallPaymentStatus status = new StallPaymentStatus();
            status.setStallId(rs.getInt("stall_id"));
            if (0 != rs.getInt("stall_renter_id")) {
                status.setHasPaidRent(true);
            } else {
                status.setHasPaidRent(false);
            }

            return status;
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
    @Override
    public Pagination<StallPaymentHistory> listStallPaymentHistory(int stallId, PaginationCriteria pagination) {
        StringBuilder sqlCountRows = new StringBuilder();
        sqlCountRows
                .append("SELECT count(*) FROM stall_payment_history sph JOIN stall_renter sr ON sph.stall_renter_id = sr.stall_renter_id WHERE sr.stall_id = ?");

        StringBuilder sqlFetchRows = new StringBuilder();
        sqlFetchRows.append("SELECT sph.*, sr.stall_id, sr.renter_id");
        sqlFetchRows.append(", (SELECT r.name FROM renter r WHERE r.renter_id = sr.renter_id) AS renter_name");
        sqlFetchRows
                .append(" FROM stall_payment_history sph JOIN stall_renter sr ON sph.stall_renter_id = sr.stall_renter_id WHERE sr.stall_id = ? ORDER BY sph.created DESC");

        List<Object> args = new ArrayList<Object>();
        args.add(stallId);

        StringBuilder condition = new StringBuilder();

        int count = this.jdbcTemplate.queryForInt(sqlCountRows.append(condition).toString(), args.toArray());

        if (0 != pagination.getLimit()) {
            condition.append(" LIMIT ?, ?");
            args.add(pagination.getOffset());
            args.add(pagination.getLimit());
        }
        List<StallPaymentHistory> list = this.jdbcTemplate.query(sqlFetchRows.append(condition).toString(),
                args.toArray(), new StallPaymentHistoryMapper());

        return new Pagination(count, list);
    }

    private static final class StallPaymentHistoryMapper implements RowMapper<StallPaymentHistory> {
        public StallPaymentHistory mapRow(ResultSet rs, int rowNum) throws SQLException {
            StallPaymentHistory history = new StallPaymentHistory();
            history.setId(rs.getInt("id"));
            history.setStallRenterId(rs.getInt("stall_renter_id"));
            history.setStartDate(rs.getDate("start_date"));
            history.setEndDate(rs.getDate("end_date"));
            history.setQuarter(rs.getInt("quarter"));
            history.setTotalPrice(rs.getInt("total_price"));
            history.setComment(rs.getString("comment"));
            history.setCreated(rs.getTimestamp("created"));

            history.setStallId(rs.getInt("stall_id"));
            history.setRenterId(rs.getInt("renter_id"));
            history.setRenterName(rs.getString("renter_name"));

            return history;
        }
    }

    @Override
    public StallPaymentHistory getById(int id) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT sph.*, sr.stall_id, sr.renter_id");
        sql.append(", (SELECT r.name FROM renter r WHERE r.renter_id = sr.renter_id) AS renter_name");
        sql.append(" FROM stall_payment_history sph JOIN stall_renter sr ON sph.stall_renter_id = sr.stall_renter_id WHERE sph.id = ?");

        List<StallPaymentHistory> list = this.jdbcTemplate.query(sql.toString(), new Object[] { id },
                new StallPaymentHistoryMapper());

        return null != list && list.size() > 0 ? list.get(0) : null;
    }
}
