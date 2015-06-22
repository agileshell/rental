package com.insoul.rental.dao;

import java.util.List;

import com.insoul.rental.model.SystemSetting;

public interface SystemSettingDao {

    List<SystemSetting> getSettings();

    void update(SystemSetting systemSetting);
}
