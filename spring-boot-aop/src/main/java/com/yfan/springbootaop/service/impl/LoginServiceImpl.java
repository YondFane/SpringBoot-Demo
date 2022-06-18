package com.yfan.springbootaop.service.impl;

import com.yfan.springbootaop.controller.LoginLogoutController;
import com.yfan.springbootaop.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * 基于接口
 * @author YFAN
 * @date 2021/11/17/017
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginLogoutController loginLogoutController;
    @Override
    public void login() {
        loginLogoutController.test();
        System.out.println(Thread.currentThread().getName()+"---"+"login() 登录");
    }
}
