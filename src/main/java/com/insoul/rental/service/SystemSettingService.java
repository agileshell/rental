package com.insoul.rental.service;

import java.util.Map;

public interface SystemSettingService extends BaseService {

    Map<String, String> getSettings();

    void updateSettings(Map<String, String> settings);
}
