package com.yfan.springbootfilter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AccessController {


    /**
    * index
    * @Author YFAN
    * @Date 2021/11/19
    * @params []
    * @return java.lang.String
    */
    @RequestMapping("index")
    @ResponseBody
    public String index(HttpServletRequest request){
        return "Hello World";
    }
}
