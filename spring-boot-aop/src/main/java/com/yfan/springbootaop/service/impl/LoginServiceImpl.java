package com.yfan.springbootaop.service.impl;

import com.yfan.springbootaop.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/*
 * 基于接口
 * @author YFAN
 * @date 2021/11/17/017
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Override
    public void login() {
        System.out.println("login() 登录");
    }
}
