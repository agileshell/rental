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

import com.insoul.rental.criteria.RenterCriteria;
import com.insoul.rental.dao.RenterDao;
import com.insoul.rental.model.Pagination;
import com.insoul.rental.model.Renter;

@Repository
public class RenterDaoImpl extends BaseDaoImpl implements RenterDao {

    @SuppressWarnings({ "unchecked", "deprecation", "rawtypes" })
    public Pagination<Renter> listRenters(RenterCriteria criteria) {
        List<Object> args = new ArrayList<Object>();

        StringBuilder sqlCountRows = new StringBuilder();
        sqlCountRows.append("SELECT count(*) FROM renter WHERE is_deleted = 0");

        StringBuilder sqlFetchRows = new StringBuilder();
        sqlFetchRows.append("SELECT * FROM renter WHERE is_deleted = 0");

        StringBuilder condition = new StringBuilder();
        if (StringUtils.isNotEmpty(criteria.getName())) {
            condition.append(" AND name LIKE ?");
            args.add("%" + criteria.getName() + "%");
        }

        int count = this.jdbcTemplate.queryForInt(sqlCountRows.append(condition).toString(), args.toArray());

        if (0 != criteria.getLimit()) {
            condition.append(" LIMIT ?, ?");
            args.add(criteria.getOffset());
            args.add(criteria.getLimit());
        }
        List<Renter> list = this.jdbcTemplate.query(sqlFetchRows.append(condition).toString(), args.toArray(),
                new RenterMapper());

        return new Pagination(count, list);
    }

    public int create(final Renter renter) {
        final String sql = "INSERT INTO renter(name, mobile, id_card, comment, created, updated) VALUES(?,?,?,?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setObject(1, renter.getName());
                ps.setObject(2, renter.getMobile());
                ps.setObject(3, renter.getIdCard());
                ps.setObject(4, renter.getComment());
                ps.setObject(5, renter.getCreated());
                ps.setObject(6, renter.getUpdated());

                return ps;
            }
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    public void update(Renter renter) {
        String sql = "UPDATE renter SET name = ?, mobile = ?, id_card = ?, comment = ?, updated = ? WHERE renter_id = ?";

        this.jdbcTemplate.update(
                sql,
                new Object[] { renter.getName(), renter.getMobile(), renter.getIdCard(), renter.getComment(),
                        renter.getUpdated(), renter.getRenterId() });
    }

    public void delete(int renterId) {
        String sql = "UPDATE renter SET is_deleted = 1, updated = ? WHERE renter_id = ?";

        this.jdbcTemplate.update(sql, new Object[] { new Timestamp(System.currentTimeMillis()), renterId });
    }

    public Renter getById(int renterId) {
        List<Renter> list = this.jdbcTemplate.query("SELECT * FROM renter WHERE renter_id = ?",
                new Object[] { renterId }, new RenterMapper());

        return null != list && list.size() > 0 ? list.get(0) : null;
    }

    private static final class RenterMapper implements RowMapper<Renter> {
        public Renter mapRow(ResultSet rs, int rowNum) throws SQLException {
            Renter renter = new Renter();
            renter.setRenterId(rs.getInt("renter_id"));
            renter.setName(rs.getString("name"));
            renter.setMobile(rs.getString("mobile"));
            renter.setIdCard(rs.getString("id_card"));
            renter.setComment(rs.getString("comment"));
            renter.setCreated(rs.getTimestamp("created"));
            renter.setUpdated(rs.getTimestamp("updated"));

            return renter;
        }
    }

    @Override
    public List<Renter> listRenters() {
        List<Renter> list = this.jdbcTemplate.query("SELECT * FROM renter WHERE is_deleted = 0", new Object[] {},
                new RenterMapper());

        return list;
    }

    @Override
    public int countRenter() {
        String sql = "SELECT count(*) FROM renter WHERE is_deleted = 0";

        @SuppressWarnings("deprecation")
        int count = this.jdbcTemplate.queryForInt(sql, new Object[] {});

        return count;
    }

}
