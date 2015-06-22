package com.insoul.rental.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.insoul.rental.criteria.PaginationCriteria;
import com.insoul.rental.dao.FlatPaymentHistoryDao;
import com.insoul.rental.model.FlatPaymentHistory;
import com.insoul.rental.model.FlatPaymentStatus;
import com.insoul.rental.model.Pagination;

@Repository
public class FlatPaymentHistoryDaoImpl extends BaseDaoImpl implements FlatPaymentHistoryDao {

    @Override
    public void create(FlatPaymentHistory flatPaymentHistory) {
        String sql = "INSERT INTO flat_payment_history(flat_renter_id, start_date, end_date, quarter, total_price, comment, created) VALUES(?,?,?,?,?,?,?)";

        List<Object> args = new ArrayList<Object>();

        args.add(flatPaymentHistory.getFlatRenterId());
        args.add(flatPaymentHistory.getStartDate());
        args.add(flatPaymentHistory.getEndDate());
        args.add(flatPaymentHistory.getQuarter());
        args.add(flatPaymentHistory.getTotalPrice());
        args.add(flatPaymentHistory.getComment());
        args.add(flatPaymentHistory.getCreated());

        this.jdbcTemplate.update(sql.toString(), args.toArray());
    }

    @Override
    public List<FlatPaymentStatus> getFlatPaymentStatus(List<Integer> ids, int quarter) {
        if (null == ids || ids.isEmpty()) {
            return new ArrayList<FlatPaymentStatus>();
        }

        String sql = "SELECT f.flat_id, fph.flat_renter_id FROM flat f LEFT JOIN flat_payment_history fph ON f.flat_renter_id = fph.flat_renter_id WHERE fph.quarter = :quarter AND f.flat_id IN(:ids) GROUP BY f.flat_id";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("quarter", quarter);
        parameters.addValue("ids", ids);

        List<FlatPaymentStatus> list = this.namedJdbcTemplate.query(sql, parameters,
                new RowMapper<FlatPaymentStatus>() {
                    public FlatPaymentStatus mapRow(ResultSet rs, int rowNumber) throws SQLException {
                        FlatPaymentStatus status = new FlatPaymentStatus();
                        status.setFlatId(rs.getInt("flat_id"));
                        if (0 != rs.getInt("flat_renter_id")) {
                            status.setHasPaidRent(true);
                        } else {
                            status.setHasPaidRent(false);
                        }

                        return status;
                    }
                });

        return list;
    }

    @SuppressWarnings({ "unchecked", "deprecation", "rawtypes" })
    @Override
    public Pagination<FlatPaymentHistory> listFlatPaymentHistory(int stallId, PaginationCriteria pagination) {
        StringBuilder sqlCountRows = new StringBuilder();
        sqlCountRows
                .append("SELECT count(*) FROM flat_payment_history fph JOIN flat_renter fr ON fph.flat_renter_id = fr.flat_renter_id WHERE fr.flat_id = ?");

        StringBuilder sqlFetchRows = new StringBuilder();
        sqlFetchRows.append("SELECT fph.*, fr.flat_id, fr.renter_id");
        sqlFetchRows.append(", (SELECT r.name FROM renter r WHERE r.renter_id = fr.renter_id) AS renter_name");
        sqlFetchRows
                .append(" FROM flat_payment_history fph JOIN flat_renter fr ON fph.flat_renter_id = fr.flat_renter_id WHERE fr.flat_id = ? ORDER BY fph.created DESC");

        List<Object> args = new ArrayList<Object>();
        args.add(stallId);

        StringBuilder condition = new StringBuilder();

        int count = this.jdbcTemplate.queryForInt(sqlCountRows.append(condition).toString(), args.toArray());

        if (0 != pagination.getLimit()) {
            condition.append(" LIMIT ?, ?");
            args.add(pagination.getOffset());
            args.add(pagination.getLimit());
        }
        List<FlatPaymentHistory> list = this.jdbcTemplate.query(sqlFetchRows.append(condition).toString(),
                args.toArray(), new FlatPaymentHistoryMapper());

        return new Pagination(count, list);
    }

    @Override
    public FlatPaymentHistory getById(int id) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT fph.*, fr.flat_id, fr.renter_id");
        sql.append(", (SELECT r.name FROM renter r WHERE r.renter_id = fr.renter_id) AS renter_name");
        sql.append(" FROM flat_payment_history fph JOIN flat_renter fr ON fph.flat_renter_id = fr.flat_renter_id WHERE fph.id = ?");

        List<FlatPaymentHistory> list = this.jdbcTemplate.query(sql.toString(), new Object[] { id },
                new FlatPaymentHistoryMapper());

        return null != list && list.size() > 0 ? list.get(0) : null;
    }

    private static final class FlatPaymentHistoryMapper implements RowMapper<FlatPaymentHistory> {
        public FlatPaymentHistory mapRow(ResultSet rs, int rowNum) throws SQLException {
            FlatPaymentHistory history = new FlatPaymentHistory();
            history.setId(rs.getInt("id"));
            history.setFlatRenterId(rs.getInt("flat_renter_id"));
            history.setStartDate(rs.getDate("start_date"));
            history.setEndDate(rs.getDate("end_date"));
            history.setQuarter(rs.getInt("quarter"));
            history.setTotalPrice(rs.getInt("total_price"));
            history.setComment(rs.getString("comment"));
            history.setCreated(rs.getTimestamp("created"));

            history.setFlatId(rs.getInt("flat_id"));
            history.setRenterId(rs.getInt("renter_id"));
            history.setRenterName(rs.getString("renter_name"));

            return history;
        }
    }
}
