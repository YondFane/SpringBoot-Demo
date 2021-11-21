package com.yfan.springbootlistener.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
public class UserController {

    /*
     * 首页
     */
    @RequestMapping("index")
    public String index(HttpServletRequest request){
        HttpSession session = request.getSession();
        log.info("session:{}第{}次访问首页",session.getId(),session.getAttribute("COUNT"));
        log.info("ACCESS:{}",request.getAttribute("ACCESS"));
        return "index";
    }



}
