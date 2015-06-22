package com.insoul.rental.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.insoul.rental.constant.SystemSettingPath;
import com.insoul.rental.service.SystemSettingService;
import com.insoul.rental.vo.request.SystemConfigRequest;

@Controller
public class SystemController extends BaseController {

    @Autowired
    private SystemSettingService systemSettingService;

    @RequestMapping("/system/configPage")
    public String getEditContentTemplatePage(Model model) {
        Map<String, String> settings = systemSettingService.getSettings();

        String watermeter = settings.get(SystemSettingPath.WATERMETER);
        model.addAttribute("watermeter", watermeter);

        String meter = settings.get(SystemSettingPath.METER);
        model.addAttribute("meter", meter);

        return "system/config";
    }

    @RequestMapping("/system/config")
    public String editContentTemplate(@ModelAttribute SystemConfigRequest systemConfigRequest, Model model) {

        Map<String, String> settings = new HashMap<String, String>();
        if (StringUtils.isNotBlank(systemConfigRequest.getWatermeter())) {
            settings.put(SystemSettingPath.WATERMETER, systemConfigRequest.getWatermeter());
        }
        if (StringUtils.isNotBlank(systemConfigRequest.getMeter())) {
            settings.put(SystemSettingPath.METER, systemConfigRequest.getMeter());
        }

        systemSettingService.updateSettings(settings);

        return "redirect:/system/configPage";
    }
}