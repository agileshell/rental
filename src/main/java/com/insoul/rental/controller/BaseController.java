package com.insoul.rental.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseController {

    String DATE_FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpSession session;

    protected boolean isLogin() {
        return (null != session.getAttribute("account")) ? true : false;
    }
}
