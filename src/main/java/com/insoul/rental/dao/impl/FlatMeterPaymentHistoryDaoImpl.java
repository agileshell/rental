package com.insoul.rental.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.insoul.rental.criteria.PaginationCriteria;
import com.insoul.rental.dao.FlatMeterPaymentHistoryDao;
import com.insoul.rental.model.FlatMeterPaymentHistory;
import com.insoul.rental.model.FlatMeterPaymentStatus;
import com.insoul.rental.model.Pagination;

@Repository
public class FlatMeterPaymentHistoryDaoImpl extends BaseDaoImpl implements FlatMeterPaymentHistoryDao {

    @Override
    public void create(FlatMeterPaymentHistory flatMeterPaymentHistory) {
        String sql = "INSERT INTO flat_meter_payment_history(flat_renter_id, first_record, last_record, quarter, price, total_price, comment, record_date, created) VALUES(?,?,?,?,?,?,?,?,?)";

        List<Object> args = new ArrayList<Object>();

        args.add(flatMeterPaymentHistory.getFlatRenterId());
        args.add(flatMeterPaymentHistory.getFirstRecord());
        args.add(flatMeterPaymentHistory.getLastRecord());
        args.add(flatMeterPaymentHistory.getQuarter());
        args.add(flatMeterPaymentHistory.getPrice());
        args.add(flatMeterPaymentHistory.getTotalPrice());
        args.add(flatMeterPaymentHistory.getComment());
        args.add(flatMeterPaymentHistory.getRecordDate());
        args.add(flatMeterPaymentHistory.getCreated());

        this.jdbcTemplate.update(sql.toString(), args.toArray());
    }

    @Override
    public List<FlatMeterPaymentStatus> getFlatPaymentStatus(List<Integer> ids, int quarter) {
        if (null == ids || ids.isEmpty()) {
            return new ArrayList<FlatMeterPaymentStatus>();
        }

        String sql = "SELECT f.flat_id, fmph.flat_renter_id FROM flat f LEFT JOIN flat_meter_payment_history fmph ON f.flat_renter_id = fmph.flat_renter_id WHERE fmph.quarter = :quarter AND f.flat_id IN(:ids) GROUP BY f.flat_id";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("quarter", quarter);
        parameters.addValue("ids", ids);
        List<FlatMeterPaymentStatus> list = this.namedJdbcTemplate.query(sql, parameters,
                new RowMapper<FlatMeterPaymentStatus>() {
                    public FlatMeterPaymentStatus mapRow(ResultSet rs, int rowNumber) throws SQLException {
                        FlatMeterPaymentStatus status = new FlatMeterPaymentStatus();
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

    @Override
    public List<FlatMeterPaymentHistory> getFlatMeterPaymentHistory(int flatRenterId) {
        String sql = "SELECT fmph.* FROM flat_meter_payment_history fmph WHERE fmph.flat_renter_id = :flatRenterId ORDER BY fmph.created DESC";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("flatRenterId", flatRenterId);

        List<FlatMeterPaymentHistory> list = this.namedJdbcTemplate.query(sql, parameters,
                new RowMapper<FlatMeterPaymentHistory>() {
                    public FlatMeterPaymentHistory mapRow(ResultSet rs, int rowNumber) throws SQLException {
                        FlatMeterPaymentHistory history = new FlatMeterPaymentHistory();
                        history.setFlatRenterId(rs.getInt("flat_renter_id"));
                        history.setFirstRecord(rs.getString("first_Record"));
                        history.setLastRecord(rs.getString("last_record"));
                        history.setQuarter(rs.getInt("quarter"));
                        history.setPrice(rs.getFloat("price"));
                        history.setTotalPrice(rs.getInt("total_price"));
                        history.setComment(rs.getString("comment"));
                        history.setRecordDate(rs.getDate("record_date"));
                        history.setCreated(rs.getTimestamp("created"));

                        return history;
                    }
                });

        return list;
    }

    @SuppressWarnings({ "deprecation", "unchecked", "rawtypes" })
    @Override
    public Pagination<FlatMeterPaymentHistory> listFlatMeterPaymentHistory(int flatId, PaginationCriteria pagination) {
        StringBuilder sqlCountRows = new StringBuilder();
        sqlCountRows
                .append("SELECT count(*) FROM flat_meter_payment_history fmph JOIN flat_renter fr ON fmph.flat_renter_id = fr.flat_renter_id WHERE fr.flat_id = ?");

        StringBuilder sqlFetchRows = new StringBuilder();
        sqlFetchRows.append("SELECT fmph.*, fr.flat_id, fr.renter_id");
        sqlFetchRows.append(", (SELECT r.name FROM renter r WHERE r.renter_id = fr.renter_id) AS renter_name");
        sqlFetchRows
                .append(" FROM flat_meter_payment_history fmph JOIN flat_renter fr ON fmph.flat_renter_id = fr.flat_renter_id WHERE fr.flat_id = ? ORDER BY fmph.created DESC");

        List<Object> args = new ArrayList<Object>();
        args.add(flatId);

        StringBuilder condition = new StringBuilder();

        int count = this.jdbcTemplate.queryForInt(sqlCountRows.append(condition).toString(), args.toArray());

        if (0 != pagination.getLimit()) {
            condition.append(" LIMIT ?, ?");
            args.add(pagination.getOffset());
            args.add(pagination.getLimit());
        }
        List<FlatMeterPaymentHistory> list = this.jdbcTemplate.query(sqlFetchRows.append(condition).toString(),
                args.toArray(), new FlatMeterPaymentHistoryMapper());

        return new Pagination(count, list);
    }

    @Override
    public FlatMeterPaymentHistory getById(int id) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT fmph.*, fr.flat_id, fr.renter_id");
        sql.append(", (SELECT r.name FROM renter r WHERE r.renter_id = fr.renter_id) AS renter_name");
        sql.append(" FROM flat_meter_payment_history fmph JOIN flat_renter fr ON fmph.flat_renter_id = fr.flat_renter_id WHERE fmph.id = ?");

        List<FlatMeterPaymentHistory> list = this.jdbcTemplate.query(sql.toString(), new Object[] { id },
                new FlatMeterPaymentHistoryMapper());

        return null != list && list.size() > 0 ? list.get(0) : null;
    }

    private static final class FlatMeterPaymentHistoryMapper implements RowMapper<FlatMeterPaymentHistory> {
        public FlatMeterPaymentHistory mapRow(ResultSet rs, int rowNum) throws SQLException {
            FlatMeterPaymentHistory history = new FlatMeterPaymentHistory();
            history.setId(rs.getInt("id"));
            history.setFlatRenterId(rs.getInt("flat_renter_id"));
            history.setFirstRecord(rs.getString("first_Record"));
            history.setLastRecord(rs.getString("last_record"));
            history.setQuarter(rs.getInt("quarter"));
            history.setPrice(rs.getFloat("price"));
            history.setTotalPrice(rs.getInt("total_price"));
            history.setComment(rs.getString("comment"));
            history.setRecordDate(rs.getDate("record_date"));
            history.setCreated(rs.getTimestamp("created"));

            history.setFlatId(rs.getInt("flat_id"));
            history.setRenterId(rs.getInt("renter_id"));
            history.setRenterName(rs.getString("renter_name"));

            return history;
        }
    }

}
