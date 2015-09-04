package com.insoul.rental.service.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.insoul.rental.model.SystemSetting;
import com.insoul.rental.service.SystemSettingService;

@Service
public class SystemSettingServiceImpl extends BaseServiceImpl implements SystemSettingService {

    @Override
    public Map<String, String> getSettings() {
        List<SystemSetting> settings = systemSettingDao.getSettings();

        Map<String, String> systemconfig = new HashMap<String, String>();
        for (SystemSetting setting : settings) {
            systemconfig.put(setting.getKey(), setting.getValue());
        }

        return systemconfig;
    }

    @Override
    public void updateSettings(Map<String, String> settings) {
        if ((null != settings) && !settings.isEmpty()) {
            Set<Entry<String, String>> entrySets = settings.entrySet();
            for (Map.Entry<String, String> m : entrySets) {
                SystemSetting siteSetting = new SystemSetting();
                siteSetting.setKey(m.getKey());
                siteSetting.setValue(m.getValue());
                siteSetting.setUpdated(new Timestamp(System.currentTimeMillis()));

                systemSettingDao.update(siteSetting);
            }
        }
    }

}
