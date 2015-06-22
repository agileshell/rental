package com.insoul.rental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.insoul.rental.dao.FlatDao;
import com.insoul.rental.dao.RenterDao;
import com.insoul.rental.dao.StallDao;

@Controller
public class AccountController extends BaseController {

    @Autowired
    private StallDao stallDao;

    @Autowired
    private FlatDao flatDao;

    @Autowired
    private RenterDao renterDao;

    @RequestMapping("/loginPage")
    public String loginView() {
        if (isLogin()) {
            return "redirect:/homePage";
        } else {
            return "index";
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginHandle(@RequestParam String account, @RequestParam String password, Model model) {
        if (account.equalsIgnoreCase("admin") && password.equals("123456")) {
            session.setAttribute("account", account);
            session.removeAttribute("login_error");

            return "redirect:/homePage";
        } else {
            session.setAttribute("login_error", "用户名或者密码错误");

            return "redirect:/loginPage";
        }
    }

    @RequestMapping(value = "/logout")
    public String logout() {
        session.removeAttribute("account");

        return "redirect:/loginPage";
    }

    @RequestMapping(value = "/homePage")
    public String home(Model model) {
        if (isLogin()) {
            int stallTotal = stallDao.countStall(null);
            int stallHasRent = stallDao.countStall(true);
            int flatTotal = flatDao.countFlat(null);
            int flatHasRent = flatDao.countFlat(true);
            int renterTotal = renterDao.countRenter();

            model.addAttribute("stallTotal", stallTotal);
            model.addAttribute("stallHasRent", stallHasRent);
            model.addAttribute("flatTotal", flatTotal);
            model.addAttribute("flatHasRent", flatHasRent);
            model.addAttribute("renterTotal", renterTotal);

            return "home";
        } else {
            return "redirect:/loginPage";
        }
    }

}