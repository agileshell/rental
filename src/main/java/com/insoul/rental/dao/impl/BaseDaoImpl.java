package com.insoul.rental.dao.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.insoul.rental.dao.BaseDao;

@Repository
public class BaseDaoImpl implements BaseDao {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    protected NamedParameterJdbcTemplate namedJdbcTemplate;

    @PostConstruct
    public void init() {
        this.namedJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

}
