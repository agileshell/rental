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

        String first_quarter_start = settings.get(SystemSettingPath.FIRST_QUARTER_START);
        model.addAttribute("first_quarter_start", first_quarter_start);
        String first_quarter_end = settings.get(SystemSettingPath.FIRST_QUARTER_END);
        model.addAttribute("first_quarter_end", first_quarter_end);

        String second_quarter_start = settings.get(SystemSettingPath.SECOND_QUARTER_START);
        model.addAttribute("second_quarter_start", second_quarter_start);
        String second_quarter_end = settings.get(SystemSettingPath.SECOND_QUARTER_END);
        model.addAttribute("second_quarter_end", second_quarter_end);

        String third_quarter_start = settings.get(SystemSettingPath.THIRD_QUARTER_START);
        model.addAttribute("third_quarter_start", third_quarter_start);
        String third_quarter_end = settings.get(SystemSettingPath.THIRD_QUARTER_END);
        model.addAttribute("third_quarter_end", third_quarter_end);

        String fourth_quarter_start = settings.get(SystemSettingPath.FOURTH_QUARTER_START);
        model.addAttribute("fourth_quarter_start", fourth_quarter_start);
        String fourth_quarter_end = settings.get(SystemSettingPath.FOURTH_QUARTER_END);
        model.addAttribute("fourth_quarter_end", fourth_quarter_end);

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