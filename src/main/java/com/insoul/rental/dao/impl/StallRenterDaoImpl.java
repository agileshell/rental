package com.insoul.rental.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.insoul.rental.dao.StallRenterDao;
import com.insoul.rental.model.StallRenter;

@Repository
public class StallRenterDaoImpl extends BaseDaoImpl implements StallRenterDao {

    public List<StallRenter> listStallRentersByStallId(int stallId) {
        StringBuilder sql = new StringBuilder("SELECT st.*, r.name AS renter_name FROM stall_renter st");
        sql.append(" JOIN renter r ON r.renter_id = st.renter_id");
        sql.append(" WHERE st.stall_id = ?");

        List<StallRenter> list = this.jdbcTemplate.query(sql.toString(), new Object[] { stallId },
                new RowMapper<StallRenter>() {
                    public StallRenter mapRow(ResultSet rs, int rowNumber) throws SQLException {
                        StallRenter stallRenter = new StallRenter();
                        stallRenter.setStallId(rs.getInt("stall_id"));
                        stallRenter.setRenterId(rs.getInt("renter_id"));
                        stallRenter.setHasRented(rs.getBoolean("has_rented"));
                        stallRenter.setRentDate(rs.getDate("rent_date"));
                        stallRenter.setUnrentDate(rs.getDate("unrent_date"));
                        stallRenter.setInitWatermeter(rs.getString("init_watermeter"));
                        stallRenter.setInitMeter(rs.getString("init_meter"));
                        stallRenter.setComment(rs.getString("comment"));
                        stallRenter.setCreated(rs.getTimestamp("created"));
                        stallRenter.setRenterName(rs.getString("renter_name"));

                        return stallRenter;
                    }
                });

        return list;
    }

    public List<StallRenter> listStallRentersByRenterId(int renterId) {
        StringBuilder sql = new StringBuilder("SELECT st.*, s.name AS stall_name FROM stall_renter st");
        sql.append(" JOIN stall s ON s.stall_id = st.stall_id AND s.is_deleted = 0");
        sql.append(" WHERE st.renter_id = ?");

        List<StallRenter> list = this.jdbcTemplate.query(sql.toString(), new Object[] { renterId },
                new RowMapper<StallRenter>() {
                    public StallRenter mapRow(ResultSet rs, int rowNumber) throws SQLException {
                        StallRenter stallRenter = new StallRenter();
                        stallRenter.setStallId(rs.getInt("stall_id"));
                        stallRenter.setRenterId(rs.getInt("renter_id"));
                        stallRenter.setHasRented(rs.getBoolean("has_rented"));
                        stallRenter.setRentDate(rs.getDate("rent_date"));
                        stallRenter.setUnrentDate(rs.getDate("unrent_date"));
                        stallRenter.setInitWatermeter(rs.getString("init_watermeter"));
                        stallRenter.setInitMeter(rs.getString("init_meter"));
                        stallRenter.setComment(rs.getString("comment"));
                        stallRenter.setCreated(rs.getTimestamp("created"));
                        stallRenter.setStallName(rs.getString("stall_name"));

                        return stallRenter;
                    }
                });

        return list;
    }

    public int create(final StallRenter stallRenter) {
        final String sql = "INSERT INTO stall_renter (stall_id, renter_id, rent_date, unrent_date, init_watermeter, init_meter, comment, created) VALUES(?,?,?,?,?,?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setObject(1, stallRenter.getStallId());
                ps.setObject(2, stallRenter.getRenterId());
                ps.setObject(3, stallRenter.getRentDate());
                ps.setObject(4, stallRenter.getUnrentDate());
                ps.setObject(5, stallRenter.getInitWatermeter());
                ps.setObject(6, stallRenter.getInitMeter());
                ps.setObject(7, stallRenter.getComment());
                ps.setObject(8, stallRenter.getCreated());

                return ps;
            }
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    public void update(StallRenter stallRenter) {
        String sql = "UPDATE stall_renter SET rent_date = ?, unrent_date = ?, init_watermeter = ?, init_meter = ?, comment = ? WHERE stall_renter_id = ?";

        this.jdbcTemplate.update(sql,
                new Object[] { stallRenter.getRentDate(), stallRenter.getUnrentDate(), stallRenter.getInitWatermeter(),
                        stallRenter.getInitMeter(), stallRenter.getComment(), stallRenter.getStallRenterId() });
    }

    public StallRenter getById(int stallRenterId) {
        final StallRenter stallRenter = new StallRenter();

        StringBuilder sql = new StringBuilder("SELECT st.*, r.name AS renter_name FROM stall_renter st");
        sql.append(" JOIN renter r ON r.renter_id = st.renter_id");
        sql.append(" WHERE st.stall_renter_id = ?");

        this.jdbcTemplate.query(sql.toString(), new Object[] { stallRenterId }, new RowCallbackHandler() {
            public void processRow(ResultSet rs) throws SQLException {
                stallRenter.setStallRenterId(rs.getInt("stall_renter_id"));
                stallRenter.setStallId(rs.getInt("stall_id"));
                stallRenter.setRenterId(rs.getInt("renter_id"));
                stallRenter.setHasRented(rs.getBoolean("has_rented"));
                stallRenter.setRentDate(rs.getDate("rent_date"));
                stallRenter.setUnrentDate(rs.getDate("unrent_date"));
                stallRenter.setInitWatermeter(rs.getString("init_watermeter"));
                stallRenter.setInitMeter(rs.getString("init_meter"));
                stallRenter.setComment(rs.getString("comment"));
                stallRenter.setCreated(rs.getTimestamp("created"));
                stallRenter.setRenterName(rs.getString("renter_name"));
            }
        });

        return 0 != stallRenter.getRenterId() ? stallRenter : null;
    }

    public void unRent(int stallRenterId) {
        String sql = "UPDATE stall_renter SET has_rented = 0 WHERE stall_renter_id = ?";

        this.jdbcTemplate.update(sql, new Object[] { stallRenterId });
    }

    @Override
    public int countStallRenter(int renterId) {
        String sql = "SELECT count(*) FROM stall_renter WHERE has_rented != 0 AND renter_id = ?";

        @SuppressWarnings("deprecation")
        int count = this.jdbcTemplate.queryForInt(sql, new Object[] { renterId });

        return count;
    }
}
