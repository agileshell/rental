package com.insoul.rental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.insoul.rental.service.SubareaService;
import com.insoul.rental.vo.SubareaVO;
import com.insoul.rental.vo.request.SubareaCreateRequest;

@Controller
@RequestMapping(value = "/subarea")
public class SubareaController extends BaseController {

    @Autowired
    private SubareaService subareaService;

    @RequestMapping("/list")
    public String listCategory(Model model) {
        model.addAttribute("subareas", subareaService.getAllSubareas());

        return "subarea/list";
    }

    @RequestMapping("/addPage")
    public String getAddSubareaPage(Model model) {
        model.addAttribute("subarea", new SubareaVO());

        return "subarea/form";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addSubarea(@ModelAttribute SubareaCreateRequest subareaCreateRequest) {
        subareaService.createSubarea(subareaCreateRequest);

        return "redirect:/subarea/list";
    }

    @RequestMapping("/editPage")
    public String getEditSubareaPage(int subareaId, Model model) {
        SubareaVO subarea = subareaService.getSubareaDetail(subareaId);
        model.addAttribute("subarea", subarea);

        return "subarea/form";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editSubarea(int subareaId, @ModelAttribute SubareaCreateRequest subareaCreateRequest) {
        subareaService.editSubarea(subareaId, subareaCreateRequest);

        return "redirect:/subarea/list";
    }

}
