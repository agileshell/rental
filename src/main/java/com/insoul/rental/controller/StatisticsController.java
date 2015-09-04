package com.insoul.rental.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.insoul.rental.criteria.FlatStatisticCriteria;
import com.insoul.rental.criteria.PaginationCriteria;
import com.insoul.rental.criteria.StallStatisticCriteria;
import com.insoul.rental.dao.FeeStatisticsDao;
import com.insoul.rental.dao.FlatDao;
import com.insoul.rental.dao.StallDao;
import com.insoul.rental.model.FeeStatistics;
import com.insoul.rental.model.FlatStatistic;
import com.insoul.rental.model.Pagination;
import com.insoul.rental.model.StallStatistic;
import com.insoul.rental.service.FlatService;
import com.insoul.rental.service.StallService;
import com.insoul.rental.vo.CurrentPage;
import com.insoul.rental.vo.FeeStatisticVO;
import com.insoul.rental.vo.request.FlatStatisticRequest;
import com.insoul.rental.vo.request.PaginationRequest;
import com.insoul.rental.vo.request.StallStatisticRequest;

@Controller
public class StatisticsController extends BaseController {

    @Autowired
    private FeeStatisticsDao feeStatisticsDao;

    @Autowired
    private FlatDao flatDao;

    @Autowired
    private StallDao stallDao;

    @Autowired
    private FlatService flatService;

    @Autowired
    private StallService stallService;

    @RequestMapping("/fee/annual")
    public String annual(Model model) {
        List<FeeStatistics> feeStatistics = feeStatisticsDao.annualStatistics();

        List<FeeStatisticVO> items = new ArrayList<FeeStatisticVO>();
        for (FeeStatistics feeStatistic : feeStatistics) {
            FeeStatisticVO item = new FeeStatisticVO();
            item.setTitle(feeStatistic.getYear());

            item.setFlatRentFee(feeStatistic.getFlatRentFee());
            item.setFlatMeterFee(feeStatistic.getFlatMeterFee());
            item.setStallRentFee(feeStatistic.getStallRentFee());
            item.setStallMeterFee(feeStatistic.getStallMeterFee());
            item.setStallWaterFee(feeStatistic.getStallWaterFee());
            item.setTotalFee(feeStatistic.getTotalFee());

            items.add(item);
        }

        model.addAttribute("items", items);

        return "fee/annual";
    }

    @RequestMapping("/fee/quarter")
    public String quarter(@ModelAttribute PaginationRequest paginationRequest, Model model) {
        PaginationCriteria criteria = new PaginationCriteria();
        int curn = paginationRequest.getCurn() > 0 ? paginationRequest.getCurn() : 1;
        int pageSize = paginationRequest.getPs();
        criteria.setOffset((curn - 1) * pageSize);
        criteria.setLimit(pageSize);
        Pagination<FeeStatistics> statistics = feeStatisticsDao.quarterStatistics(criteria);

        @SuppressWarnings({ "rawtypes", "unchecked" })
        CurrentPage<FeeStatisticVO> result = new CurrentPage(curn, statistics.getCount(), pageSize,
                formatQuarter(statistics.getItems()));

        int ps = paginationRequest.getPs();
        model.addAttribute("curn", curn);
        model.addAttribute("totaln", result.getTotaln());
        model.addAttribute("items", result.getItems());
        model.addAttribute("ps", ps);

        return "fee/quarter";
    }

    private List<FeeStatisticVO> formatQuarter(List<FeeStatistics> feeStatistics) {
        List<FeeStatisticVO> items = new ArrayList<FeeStatisticVO>();

        for (FeeStatistics feeStatistic : feeStatistics) {
            FeeStatisticVO item = new FeeStatisticVO();
            item.setTitle(feeStatistic.getYear() + "年 第" + feeStatistic.getQuarter() + "季度");

            item.setFlatRentFee(feeStatistic.getFlatRentFee());
            item.setFlatMeterFee(feeStatistic.getFlatMeterFee());
            item.setStallRentFee(feeStatistic.getStallRentFee());
            item.setStallMeterFee(feeStatistic.getStallMeterFee());
            item.setStallWaterFee(feeStatistic.getStallWaterFee());
            item.setTotalFee(feeStatistic.getTotalFee());

            items.add(item);
        }

        return items;
    }

    @RequestMapping("/flat/statistic")
    public String flatStatistic(@ModelAttribute FlatStatisticRequest requstData, Model model) {
        Map<String, Integer> quarterInfo = getQuarterInfo(new Date());
        int quarter = quarterInfo.get("quarter");
        String year = quarterInfo.get("year") + "";

        flatService.generateFlatFeeStatistics(year, quarter);

        FlatStatisticCriteria criteria = new FlatStatisticCriteria();

        StringBuilder condition = new StringBuilder();
        condition.append("&type=").append(requstData.getType());
        criteria.setType(requstData.getType());
        condition.append("&isPaid=").append(requstData.getIsPaid());
        criteria.setPaid(requstData.getIsPaid());

        if (null == requstData.getYear()) {
            condition.append("&year=").append(year);
            criteria.setYear(year);
            requstData.setYear(year);
        } else if (StringUtils.isNotEmpty(requstData.getYear())) {
            condition.append("&year=").append(requstData.getYear());
            criteria.setYear(requstData.getYear());
        }

        if (null != requstData.getQuarter()) {
            condition.append("&quarter=").append(requstData.getQuarter());
            criteria.setQuarter(requstData.getQuarter());
        } else {
            condition.append("&quarter=").append(quarter);
            criteria.setQuarter(quarter);
            requstData.setQuarter(quarter);
        }

        if (StringUtils.isNotEmpty(requstData.getFlatName())) {
            condition.append("&flatName=").append(requstData.getFlatName());
            criteria.setFlatName(requstData.getFlatName());
        }
        if (StringUtils.isNotEmpty(requstData.getRentor())) {
            condition.append("&rentor=").append(requstData.getRentor());
            criteria.setRentor(requstData.getRentor());
        }

        model.addAttribute("request", requstData);
        model.addAttribute("condition", condition.toString());

        int curn = requstData.getCurn() > 0 ? requstData.getCurn() : 1;
        int pageSize = requstData.getPs();
        criteria.setOffset((curn - 1) * pageSize);
        criteria.setLimit(pageSize);
        Pagination<FlatStatistic> statistics = flatDao.flatStatistic(criteria);

        @SuppressWarnings({ "rawtypes", "unchecked" })
        CurrentPage<FlatStatistic> result = new CurrentPage(curn, statistics.getCount(), pageSize,
                statistics.getItems());

        int ps = requstData.getPs();
        model.addAttribute("curn", curn);
        model.addAttribute("totaln", result.getTotaln());
        model.addAttribute("items", result.getItems());
        model.addAttribute("ps", ps);

        return "flat/statistic";
    }

    @RequestMapping("/stall/statistic")
    public String stallStatistic(@ModelAttribute StallStatisticRequest requstData, Model model) {
        Map<String, Integer> quarterInfo = getQuarterInfo(new Date());
        int quarter = quarterInfo.get("quarter");
        String year = quarterInfo.get("year") + "";

        stallService.generateStallFeeStatistics(year, quarter);

        StallStatisticCriteria criteria = new StallStatisticCriteria();

        StringBuilder condition = new StringBuilder();
        condition.append("&type=").append(requstData.getType());
        criteria.setType(requstData.getType());
        condition.append("&isPaid=").append(requstData.getIsPaid());
        criteria.setPaid(requstData.getIsPaid());

        if (null == requstData.getYear()) {
            condition.append("&year=").append(year);
            criteria.setYear(year);
            requstData.setYear(year);
        } else if (StringUtils.isNotEmpty(requstData.getYear())) {
            condition.append("&year=").append(requstData.getYear());
            criteria.setYear(requstData.getYear());
        }

        if (null != requstData.getQuarter()) {
            condition.append("&quarter=").append(requstData.getQuarter());
            criteria.setQuarter(requstData.getQuarter());
        } else {
            condition.append("&quarter=").append(quarter);
            criteria.setQuarter(quarter);
            requstData.setQuarter(quarter);
        }

        if (StringUtils.isNotEmpty(requstData.getStallName())) {
            condition.append("&stallName=").append(requstData.getStallName());
            criteria.setStallName(requstData.getStallName());
        }
        if (StringUtils.isNotEmpty(requstData.getRentor())) {
            condition.append("&rentor=").append(requstData.getRentor());
            criteria.setRentor(requstData.getRentor());
        }

        model.addAttribute("request", requstData);
        model.addAttribute("condition", condition.toString());

        int curn = requstData.getCurn() > 0 ? requstData.getCurn() : 1;
        int pageSize = requstData.getPs();
        criteria.setOffset((curn - 1) * pageSize);
        criteria.setLimit(pageSize);
        Pagination<StallStatistic> statistics = stallDao.flatStatistic(criteria);

        @SuppressWarnings({ "rawtypes", "unchecked" })
        CurrentPage<StallStatistic> result = new CurrentPage(curn, statistics.getCount(), pageSize,
                statistics.getItems());

        int ps = requstData.getPs();
        model.addAttribute("curn", curn);
        model.addAttribute("totaln", result.getTotaln());
        model.addAttribute("items", result.getItems());
        model.addAttribute("ps", ps);

        return "stall/statistic";
    }

}
