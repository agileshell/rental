package com.insoul.rental.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.insoul.rental.constant.ResponseStatus;
import com.insoul.rental.service.RenterService;
import com.insoul.rental.vo.CurrentPage;
import com.insoul.rental.vo.RenterDetailVO;
import com.insoul.rental.vo.RenterListVO;
import com.insoul.rental.vo.request.RenterCreateRequest;
import com.insoul.rental.vo.request.RenterListRequest;

@Controller
@RequestMapping(value = "/renter")
public class RenterController extends BaseController {

    @Autowired
    private RenterService renterService;

    @RequestMapping("/list")
    public String listContent(@ModelAttribute RenterListRequest renterListRequest, Model model) {
        CurrentPage<RenterListVO> result = renterService.listRenters(renterListRequest);
        model.addAttribute("request", renterListRequest);

        StringBuilder condition = new StringBuilder();
        if (StringUtils.isNotEmpty(renterListRequest.getName())) {
            condition.append("&name=").append(renterListRequest.getName());
        }
        model.addAttribute("condition", condition.toString());

        int curn = result.getCurn();
        int ps = renterListRequest.getPs();
        model.addAttribute("curn", curn);
        model.addAttribute("totaln", result.getTotaln());
        model.addAttribute("renters", result.getItems());
        model.addAttribute("ps", ps);

        return "renter/list";
    }

    @RequestMapping("/addPage")
    public String getAddRenterPage(Model model) {
        model.addAttribute("renter", new RenterDetailVO());

        return "renter/form";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addRenter(@ModelAttribute RenterCreateRequest renterCreateRequest) {
        renterService.createRenter(renterCreateRequest);

        return "redirect:/renter/list";
    }

    @RequestMapping("/editPage")
    public String getEditRenterPage(int renterId, Model model) {
        RenterDetailVO renter = renterService.getRenterDetail(renterId);
        model.addAttribute("renter", renter);

        return "renter/form";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editRenter(int renterId, @ModelAttribute RenterCreateRequest renterCreateRequest) {
        renterService.editRenter(renterId, renterCreateRequest);

        return "redirect:/renter/list";
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Map<String, Object> deleteRenter(int renterId) {
        int status = renterService.deleteRenter(renterId);

        Map<String, Object> result = new HashMap<String, Object>();
        if (status > 0) {
            result.put("status", ResponseStatus.SUCCEED.getValue());
        } else {
            result.put("status", ResponseStatus.FAILED.getValue());
            result.put("error_code", status);
        }

        return result;
    }
}
