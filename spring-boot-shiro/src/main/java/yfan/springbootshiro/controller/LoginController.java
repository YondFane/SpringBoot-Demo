package yfan.springbootshiro.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 登录
 * @author YFAN
 * @date 2021/11/22/022
 */
@Controller
@Slf4j
public class LoginController {

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(@RequestParam String username, @RequestParam String password, Model model){
        // 获取主题
        Subject subject = SecurityUtils.getSubject();
        // 令牌
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
        // 执行认证
        try {
            subject.login(usernamePasswordToken);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        if (subject.isAuthenticated()) {
            // 认证成功
            model.addAttribute("username", username);
            return "login";
        } else {
            // 清空令牌
            usernamePasswordToken.clear();
            return "loginError";
        }
    }

}
