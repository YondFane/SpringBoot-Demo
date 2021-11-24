package yfan.springbootshiro.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
* 首页
* @Author YFAN
* @Date 2021/11/24 
*/
@Controller
@Slf4j
public class HomeController {

    /**
     * 首页
     * 在index中登录才能正常访问
     * @author YFAN
     * @date 2021/11/22/022
     */
    @RequestMapping("login")
    public String index(){
        return "index";
    }

    /**
     * 无权限页面
     * @author YFAN
     * @date 2021/11/22/022
     */
    @RequestMapping("notRole")
    public String notRole(){
        return "notRole";
    }

}
