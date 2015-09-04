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

import com.insoul.rental.dao.FlatRenterDao;
import com.insoul.rental.model.FlatRenter;

@Repository
public class FlatRenterDaoImpl extends BaseDaoImpl implements FlatRenterDao {

    @Override
    public List<FlatRenter> listFlatRentersByFlatId(int flatId) {
        StringBuilder sql = new StringBuilder("SELECT fr.*, r.name AS renter_name FROM flat_renter fr");
        sql.append(" JOIN renter r ON r.renter_id = fr.renter_id");
        sql.append(" WHERE fr.flat_id = ?");

        List<FlatRenter> list = this.jdbcTemplate.query(sql.toString(), new Object[] { flatId },
                new RowMapper<FlatRenter>() {
                    public FlatRenter mapRow(ResultSet rs, int rowNumber) throws SQLException {
                        FlatRenter flatRenter = new FlatRenter();
                        flatRenter.setFlatId(rs.getInt("flat_id"));
                        flatRenter.setRenterId(rs.getInt("renter_id"));
                        flatRenter.setHasRented(rs.getBoolean("has_rented"));
                        flatRenter.setRentDate(rs.getDate("rent_date"));
                        flatRenter.setUnrentDate(rs.getDate("unrent_date"));
                        flatRenter.setInitMeter(rs.getString("init_meter"));
                        flatRenter.setComment(rs.getString("comment"));
                        flatRenter.setCreated(rs.getTimestamp("created"));
                        flatRenter.setRenterName(rs.getString("renter_name"));

                        return flatRenter;
                    }
                });

        return list;
    }

    @Override
    public List<FlatRenter> listFlatRentersByRenterId(int renterId) {
        StringBuilder sql = new StringBuilder("SELECT fr.*, f.name AS flat_name FROM flat_renter fr");
        sql.append(" JOIN flat f ON f.flat_id = fr.flat_id AND f.is_deleted = 0");
        sql.append(" WHERE fr.renter_id = ?");

        List<FlatRenter> list = this.jdbcTemplate.query(sql.toString(), new Object[] { renterId },
                new RowMapper<FlatRenter>() {
                    public FlatRenter mapRow(ResultSet rs, int rowNumber) throws SQLException {
                        FlatRenter flatRenter = new FlatRenter();
                        flatRenter.setFlatId(rs.getInt("flat_id"));
                        flatRenter.setRenterId(rs.getInt("renter_id"));
                        flatRenter.setHasRented(rs.getBoolean("has_rented"));
                        flatRenter.setRentDate(rs.getDate("rent_date"));
                        flatRenter.setUnrentDate(rs.getDate("unrent_date"));
                        flatRenter.setInitMeter(rs.getString("init_meter"));
                        flatRenter.setComment(rs.getString("comment"));
                        flatRenter.setCreated(rs.getTimestamp("created"));
                        flatRenter.setFlatName(rs.getString("flat_name"));

                        return flatRenter;
                    }
                });

        return list;
    }

    @Override
    public int create(final FlatRenter flatRenter) {
        final String sql = "INSERT INTO flat_renter (flat_id, renter_id, rent_date, unrent_date, init_meter, comment, created) VALUES(?,?,?,?,?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setObject(1, flatRenter.getFlatId());
                ps.setObject(2, flatRenter.getRenterId());
                ps.setObject(3, flatRenter.getRentDate());
                ps.setObject(4, flatRenter.getUnrentDate());
                ps.setObject(5, flatRenter.getInitMeter());
                ps.setObject(6, flatRenter.getComment());
                ps.setObject(7, flatRenter.getCreated());

                return ps;
            }
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    @Override
    public void update(FlatRenter flatRenter) {
        String sql = "UPDATE flat_renter SET rent_date = ?, unrent_date = ?, init_meter = ?, comment = ? WHERE flat_renter_id = ?";

        this.jdbcTemplate.update(sql,
                new Object[] { flatRenter.getRentDate(), flatRenter.getUnrentDate(), flatRenter.getInitMeter(),
                        flatRenter.getComment(), flatRenter.getFlatRenterId() });
    }

    @Override
    public FlatRenter getById(int flatRenterId) {
        final FlatRenter flatRenter = new FlatRenter();

        StringBuilder sql = new StringBuilder("SELECT fr.*, r.name AS renter_name FROM flat_renter fr");
        sql.append(" JOIN renter r ON r.renter_id = fr.renter_id");
        sql.append(" WHERE fr.flat_renter_id = ?");

        this.jdbcTemplate.query(sql.toString(), new Object[] { flatRenterId }, new RowCallbackHandler() {
            public void processRow(ResultSet rs) throws SQLException {
                flatRenter.setFlatRenterId(rs.getInt("flat_renter_id"));
                flatRenter.setFlatId(rs.getInt("flat_id"));
                flatRenter.setRenterId(rs.getInt("renter_id"));
                flatRenter.setHasRented(rs.getBoolean("has_rented"));
                flatRenter.setRentDate(rs.getDate("rent_date"));
                flatRenter.setUnrentDate(rs.getDate("unrent_date"));
                flatRenter.setInitMeter(rs.getString("init_meter"));
                flatRenter.setComment(rs.getString("comment"));
                flatRenter.setCreated(rs.getTimestamp("created"));
                flatRenter.setRenterName(rs.getString("renter_name"));
            }
        });

        return 0 != flatRenter.getRenterId() ? flatRenter : null;
    }

    @Override
    public void unRent(int flatRenterId) {
        String sql = "UPDATE flat_renter SET has_rented = 0 WHERE flat_renter_id = ?";

        this.jdbcTemplate.update(sql, new Object[] { flatRenterId });
    }

    @Override
    public int countFlatRenter(int renterId) {
        String sql = "SELECT count(*) FROM flat_renter WHERE has_rented != 0 AND renter_id = ?";

        @SuppressWarnings("deprecation")
        int count = this.jdbcTemplate.queryForInt(sql, new Object[] { renterId });

        return count;
    }

}
