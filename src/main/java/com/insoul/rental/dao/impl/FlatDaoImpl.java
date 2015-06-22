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

import com.insoul.rental.criteria.FlatCriteria;
import com.insoul.rental.dao.FlatDao;
import com.insoul.rental.model.Flat;
import com.insoul.rental.model.Pagination;

@Repository
public class FlatDaoImpl extends BaseDaoImpl implements FlatDao {

    @SuppressWarnings({ "unchecked", "deprecation", "rawtypes" })
    @Override
    public Pagination<Flat> listFlats(FlatCriteria criteria) {
        List<Object> args = new ArrayList<Object>();

        StringBuilder sqlCountRows = new StringBuilder();
        sqlCountRows.append("SELECT count(*) FROM flat f WHERE f.is_deleted = 0");

        StringBuilder sqlFetchRows = new StringBuilder();
        sqlFetchRows
                .append("SELECT f.*, r.name AS renter_name FROM flat f LEFT JOIN renter r ON f.renter_id = r.renter_id WHERE f.is_deleted = 0");

        StringBuilder condition = new StringBuilder();
        if (null != criteria.getIsRented()) {
            if (criteria.getIsRented()) {
                condition.append(" AND f.renter_id IS NOT NULL");
            } else {
                condition.append(" AND f.renter_id IS NULL");
            }
        }
        if (null != criteria.getRenterId()) {
            condition.append(" AND f.renter_id = ?");
            args.add(criteria.getRenterId());
        }
        if (StringUtils.isNotEmpty(criteria.getName())) {
            condition.append(" AND f.name LIKE ?");
            args.add("%" + criteria.getName() + "%");
        }

        int count = this.jdbcTemplate.queryForInt(sqlCountRows.append(condition).toString(), args.toArray());

        if (0 != criteria.getLimit()) {
            condition.append(" LIMIT ?, ?");
            args.add(criteria.getOffset());
            args.add(criteria.getLimit());
        }
        List<Flat> list = this.jdbcTemplate.query(sqlFetchRows.append(condition).toString(), args.toArray(),
                new FlatMapper());

        return new Pagination(count, list);
    }

    @Override
    public int create(final Flat flat) {
        final String sql = "INSERT INTO flat(name, month_price, comment, created, updated) VALUES(?,?,?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setObject(1, flat.getName());
                ps.setObject(2, flat.getMonthPrice());
                ps.setObject(3, flat.getComment());
                ps.setObject(4, flat.getCreated());
                ps.setObject(5, flat.getUpdated());

                return ps;
            }
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    @Override
    public void update(Flat flat) {
        List<Object> args = new ArrayList<Object>();

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE flat SET name = ?, month_price = ?, comment = ?, updated = ?");
        args.add(flat.getName());
        args.add(flat.getMonthPrice());
        args.add(flat.getComment());
        args.add(flat.getUpdated());

        if (0 != flat.getRenterId()) {
            sql.append(", renter_id = ?");
            args.add(flat.getRenterId());
        } else {
            sql.append(", renter_id = NULL");
        }
        if (0 != flat.getFlatRenterId()) {
            sql.append(", flat_renter_id = ?");
            args.add(flat.getFlatRenterId());
        } else {
            sql.append(", flat_renter_id = NULL");
        }

        sql.append(" WHERE flat_id = ?");
        args.add(flat.getFlatId());

        this.jdbcTemplate.update(sql.toString(), args.toArray());
    }

    @Override
    public void delete(int flatId) {
        String sql = "UPDATE flat SET is_deleted = 1, updated = ? WHERE flat_id = ?";

        this.jdbcTemplate.update(sql, new Object[] { new Timestamp(System.currentTimeMillis()), flatId });
    }

    @Override
    public Flat getById(int flatId) {
        List<Flat> list = this.jdbcTemplate
                .query("SELECT f.*, r.name AS renter_name FROM flat f LEFT JOIN renter r ON f.renter_id = r.renter_id WHERE f.flat_id = ?",
                        new Object[] { flatId }, new FlatMapper());

        return null != list && list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public void unRent(int flatId) {
        String sql = "UPDATE flat SET renter_id = NULL, flat_renter_id = NULL, updated = ? WHERE flat_id = ?";

        this.jdbcTemplate.update(sql, new Object[] { new Timestamp(System.currentTimeMillis()), flatId });
    }

    private static final class FlatMapper implements RowMapper<Flat> {
        public Flat mapRow(ResultSet rs, int rowNum) throws SQLException {
            Flat flat = new Flat();
            flat.setFlatId(rs.getInt("flat_id"));
            flat.setName(rs.getString("name"));
            flat.setMonthPrice(rs.getInt("month_price"));
            flat.setRenterId(rs.getInt("renter_id"));
            flat.setFlatRenterId(rs.getInt("flat_renter_id"));
            flat.setComment(rs.getString("comment"));
            flat.setCreated(rs.getTimestamp("created"));
            flat.setUpdated(rs.getTimestamp("updated"));
            flat.setRenterName(rs.getString("renter_name"));

            return flat;
        }
    }

    @Override
    public int countFlat(Boolean hasRent) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT count(*) FROM flat WHERE is_deleted = 0");

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

}
