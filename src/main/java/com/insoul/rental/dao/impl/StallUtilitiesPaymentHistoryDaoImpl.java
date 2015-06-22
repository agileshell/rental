package com.insoul.rental.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.insoul.rental.criteria.PaginationCriteria;
import com.insoul.rental.dao.StallUtilitiesPaymentHistoryDao;
import com.insoul.rental.model.Pagination;
import com.insoul.rental.model.StallUtilitiesPaymentHistory;
import com.insoul.rental.model.StallUtilitiesPaymentStatus;

@Repository
public class StallUtilitiesPaymentHistoryDaoImpl extends BaseDaoImpl implements StallUtilitiesPaymentHistoryDao {

    @Override
    public void create(StallUtilitiesPaymentHistory stallUtilitiesPaymentHistory) {
        String sql = "INSERT INTO stall_utilities_payment_history(stall_renter_id, first_record, last_record, `type`, quarter, price, total_price, comment, record_date, created) VALUES(?,?,?,?,?,?,?,?,?,?)";

        List<Object> args = new ArrayList<Object>();

        args.add(stallUtilitiesPaymentHistory.getStallRenterId());
        args.add(stallUtilitiesPaymentHistory.getFirstRecord());
        args.add(stallUtilitiesPaymentHistory.getLastRecord());
        args.add(stallUtilitiesPaymentHistory.getType());
        args.add(stallUtilitiesPaymentHistory.getQuarter());
        args.add(stallUtilitiesPaymentHistory.getPrice());
        args.add(stallUtilitiesPaymentHistory.getTotalPrice());
        args.add(stallUtilitiesPaymentHistory.getComment());
        args.add(stallUtilitiesPaymentHistory.getRecordDate());
        args.add(stallUtilitiesPaymentHistory.getCreated());

        this.jdbcTemplate.update(sql.toString(), args.toArray());
    }

    @Override
    public List<StallUtilitiesPaymentStatus> getStallPaymentStatus(List<Integer> ids, int quarter) {
        if (null == ids || ids.isEmpty()) {
            return new ArrayList<StallUtilitiesPaymentStatus>();
        }

        String sql = "SELECT s.stall_id, suph.stall_renter_id, suph.`type` FROM stall s LEFT JOIN stall_utilities_payment_history suph ON s.stall_renter_id = suph.stall_renter_id WHERE suph.quarter = :quarter AND s.stall_id IN(:ids) GROUP BY s.stall_id, suph.`type`";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("quarter", quarter);
        parameters.addValue("ids", ids);
        List<StallUtilitiesPaymentStatus> list = this.namedJdbcTemplate.query(sql, parameters,
                new StallPaymentStatusMapper());

        return list;
    }

    private static final class StallPaymentStatusMapper implements RowMapper<StallUtilitiesPaymentStatus> {
        public StallUtilitiesPaymentStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
            StallUtilitiesPaymentStatus status = new StallUtilitiesPaymentStatus();
            status.setStallId(rs.getInt("stall_id"));
            status.setType(rs.getInt("type"));
            if (0 != rs.getInt("stall_renter_id")) {
                status.setHasPaid(true);
            } else {
                status.setHasPaid(false);
            }

            return status;
        }
    }

    @Override
    public List<StallUtilitiesPaymentHistory> getStallUtilitiesPaymentHistory(int stallRenterId, int type) {
        String sql = "SELECT suph.* FROM stall_utilities_payment_history suph WHERE suph.stall_renter_id = :stallRenterId AND suph.`type` = :type ORDER BY suph.created DESC";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("stallRenterId", stallRenterId);
        parameters.addValue("type", type);

        List<StallUtilitiesPaymentHistory> list = this.namedJdbcTemplate.query(sql, parameters,
                new StallUtilitiesPaymentHistoryMapperOne());

        return list;
    }

    private static final class StallUtilitiesPaymentHistoryMapperOne implements RowMapper<StallUtilitiesPaymentHistory> {
        public StallUtilitiesPaymentHistory mapRow(ResultSet rs, int rowNum) throws SQLException {
            StallUtilitiesPaymentHistory history = new StallUtilitiesPaymentHistory();
            history.setStallRenterId(rs.getInt("stall_renter_id"));
            history.setFirstRecord(rs.getString("first_Record"));
            history.setLastRecord(rs.getString("last_record"));
            history.setType(rs.getInt("type"));
            history.setQuarter(rs.getInt("quarter"));
            history.setPrice(rs.getFloat("price"));
            history.setTotalPrice(rs.getInt("total_price"));
            history.setComment(rs.getString("comment"));
            history.setRecordDate(rs.getDate("record_date"));
            history.setCreated(rs.getTimestamp("created"));

            return history;
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
    @Override
    public Pagination<StallUtilitiesPaymentHistory> listStallUtilitiesPaymentHistory(int stallId, int type,
            PaginationCriteria pagination) {
        StringBuilder sqlCountRows = new StringBuilder();
        sqlCountRows
                .append("SELECT count(*) FROM stall_utilities_payment_history suph JOIN stall_renter sr ON suph.stall_renter_id = sr.stall_renter_id WHERE sr.stall_id = ? AND suph.`type` = ?");

        StringBuilder sqlFetchRows = new StringBuilder();
        sqlFetchRows.append("SELECT suph.*, sr.stall_id, sr.renter_id");
        sqlFetchRows.append(", (SELECT r.name FROM renter r WHERE r.renter_id = sr.renter_id) AS renter_name");
        sqlFetchRows
                .append(" FROM stall_utilities_payment_history suph JOIN stall_renter sr ON suph.stall_renter_id = sr.stall_renter_id WHERE sr.stall_id = ? AND suph.`type` = ? ORDER BY suph.created DESC");

        List<Object> args = new ArrayList<Object>();
        args.add(stallId);
        args.add(type);

        StringBuilder condition = new StringBuilder();

        int count = this.jdbcTemplate.queryForInt(sqlCountRows.append(condition).toString(), args.toArray());

        if (0 != pagination.getLimit()) {
            condition.append(" LIMIT ?, ?");
            args.add(pagination.getOffset());
            args.add(pagination.getLimit());
        }
        List<StallUtilitiesPaymentHistory> list = this.jdbcTemplate.query(sqlFetchRows.append(condition).toString(),
                args.toArray(), new StallUtilitiesPaymentHistoryMapperTwo());

        return new Pagination(count, list);
    }

    private static final class StallUtilitiesPaymentHistoryMapperTwo implements RowMapper<StallUtilitiesPaymentHistory> {
        public StallUtilitiesPaymentHistory mapRow(ResultSet rs, int rowNum) throws SQLException {
            StallUtilitiesPaymentHistory history = new StallUtilitiesPaymentHistory();
            history.setId(rs.getInt("id"));
            history.setStallRenterId(rs.getInt("stall_renter_id"));
            history.setFirstRecord(rs.getString("first_Record"));
            history.setLastRecord(rs.getString("last_record"));
            history.setType(rs.getInt("type"));
            history.setQuarter(rs.getInt("quarter"));
            history.setPrice(rs.getFloat("price"));
            history.setTotalPrice(rs.getInt("total_price"));
            history.setComment(rs.getString("comment"));
            history.setRecordDate(rs.getDate("record_date"));
            history.setCreated(rs.getTimestamp("created"));

            history.setStallId(rs.getInt("stall_id"));
            history.setRenterId(rs.getInt("renter_id"));
            history.setRenterName(rs.getString("renter_name"));

            return history;
        }
    }

    @Override
    public StallUtilitiesPaymentHistory getById(int id) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT suph.*, sr.stall_id, sr.renter_id");
        sql.append(", (SELECT r.name FROM renter r WHERE r.renter_id = sr.renter_id) AS renter_name");
        sql.append(" FROM stall_utilities_payment_history suph JOIN stall_renter sr ON suph.stall_renter_id = sr.stall_renter_id WHERE suph.id = ?");

        List<StallUtilitiesPaymentHistory> list = this.jdbcTemplate.query(sql.toString(), new Object[] { id },
                new StallUtilitiesPaymentHistoryMapperTwo());

        return null != list && list.size() > 0 ? list.get(0) : null;
    }

}
