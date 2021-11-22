package yfan.springbootshiro.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

/*
 * 用户请求处理
 * @author YFAN
 * @date 2021/11/22/022
 */
@Controller
@RequestMapping("user")
public class UserController {

    /*
     * 用户信息
     * @author YFAN
     * @date 2021/11/22/022
     * @param  * @param
     * @return java.lang.String
     */
    @RequiresPermissions("user:info")//符合user:info权限要求才能访问
    @RequestMapping("info/{username}")
    public ModelAndView info(@PathVariable String username){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("info");
        modelAndView.addObject("username", username);
        return modelAndView;
    }

    //模拟无权限访问
    @RequiresPermissions("user:info2")//符合user:info2权限要求才能访问
    @RequestMapping("info2/{username}")
    public ModelAndView info2(@PathVariable String username){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("info");
        modelAndView.addObject("username", username);
        return modelAndView;
    }
}
