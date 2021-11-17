package com.yfan.springbootaop.controller;

import com.yfan.springbootaop.service.LoginService;
import com.yfan.springbootaop.service.impl.LogOutServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * 登录注销
 * @author YFAN
 * @date 2021/11/17/017
 */
@Controller
public class LoginLogoutController {

    /*基于接口*/
    @Autowired
    LoginService loginService;

    @Autowired
    LogOutServiceImpl logOutService;

    @RequestMapping("login")
    public String login() {
        loginService.login();
        return "登录成功！！！";
    }

    @RequestMapping("logout")
    public String logout(){
        logOutService.logout();
        return "注销成功！！!";
    }


}
