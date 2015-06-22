package com.insoul.rental.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.insoul.rental.dao.SubareaDao;
import com.insoul.rental.model.Subarea;

@Repository
public class SubareaDaoImpl extends BaseDaoImpl implements SubareaDao {

    @Override
    public void create(Subarea subarea) {
        List<Object> args = new ArrayList<Object>();

        StringBuilder sql = new StringBuilder("INSERT INTO subarea(name, comment, created, updated) VALUES(?,?,?,?)");
        args.add(subarea.getName());
        args.add(subarea.getComment());
        args.add(subarea.getCreated());
        args.add(subarea.getUpdated());

        this.jdbcTemplate.update(sql.toString(), args.toArray());
    }

    @Override
    public void update(Subarea subarea) {
        List<Object> args = new ArrayList<Object>();

        StringBuilder sql = new StringBuilder(
                "UPDATE subarea SET name = ?, comment = ?, updated = ? WHERE subarea_id = ?");
        args.add(subarea.getName());
        args.add(subarea.getComment());
        args.add(subarea.getUpdated());
        args.add(subarea.getSubareaId());

        this.jdbcTemplate.update(sql.toString(), args.toArray());
    }

    @Override
    public Subarea getById(int subareaId) {
        List<Subarea> list = this.jdbcTemplate.query("SELECT * FROM subarea WHERE subarea_id = ?",
                new Object[] { subareaId }, new SubareaMapper());

        return null != list && list.size() > 0 ? list.get(0) : new Subarea();
    }

    @Override
    public List<Subarea> listSubareas() {
        List<Subarea> list = this.jdbcTemplate.query("SELECT * FROM subarea", new Object[] {}, new SubareaMapper());

        return list;
    }

    @Override
    public List<Subarea> getSubareasByIds(List<Integer> ids) {
        if (null == ids || ids.isEmpty()) {
            return new ArrayList<Subarea>();
        }

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("ids", ids);

        List<Subarea> list = this.namedJdbcTemplate.query("SELECT * FROM subarea WHERE subarea_id IN(:ids)",
                parameters, new SubareaMapper());

        return list;
    }

    private static final class SubareaMapper implements RowMapper<Subarea> {
        public Subarea mapRow(ResultSet rs, int rowNum) throws SQLException {
            Subarea subarea = new Subarea();

            subarea.setSubareaId(rs.getInt("subarea_id"));
            subarea.setName(rs.getString("name"));
            subarea.setComment(rs.getString("comment"));
            subarea.setCreated(rs.getTimestamp("created"));
            subarea.setUpdated(rs.getTimestamp("updated"));

            return subarea;
        }
    }

}
