package com.insoul.rental.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.insoul.rental.dao.SystemSettingDao;
import com.insoul.rental.model.SystemSetting;

@Repository
public class SystemSettingDaoImpl extends BaseDaoImpl implements SystemSettingDao {

    @Override
    public List<SystemSetting> getSettings() {
        List<SystemSetting> list = this.jdbcTemplate.query("SELECT * FROM system_setting", new Object[] {},
                new SystemSettingMapper());

        return list;
    }

    @Override
    public void update(SystemSetting systemSetting) {
        String sql = "UPDATE system_setting SET `value` = ?, updated = ? WHERE `key` = ?";

        this.jdbcTemplate.update(sql.toString(), new Object[] { systemSetting.getValue(), systemSetting.getUpdated(),
                systemSetting.getKey() });
    }

    private static final class SystemSettingMapper implements RowMapper<SystemSetting> {
        public SystemSetting mapRow(ResultSet rs, int rowNum) throws SQLException {
            SystemSetting systemSetting = new SystemSetting();
            systemSetting.setSystemSettingId(rs.getInt("system_setting_id"));
            systemSetting.setKey(rs.getString("key"));
            systemSetting.setValue(rs.getString("value"));

            return systemSetting;
        }
    }
}
