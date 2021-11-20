package com.yfan.springboothandlerinterceptor.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
public class UserController {

    @RequestMapping("index")
    public String index(){
        return "index";
    }

    /*
     * 模拟登录
     */
    @RequestMapping("login")
    public String login(HttpServletRequest request){
        // 设置session
        HttpSession session = request.getSession();
        session.setAttribute("ACCESS", "true");
        // 获取请求参数access
        String access = request.getParameter("access");
        log.info("login中的access参数值：{}",access);
        log.info((String) request.getAttribute("access"));
        return "login";
    }


}
