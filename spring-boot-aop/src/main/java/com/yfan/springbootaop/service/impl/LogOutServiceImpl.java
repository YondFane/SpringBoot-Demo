package com.yfan.springbootaop.service.impl;

import org.springframework.stereotype.Service;

/*
 * 不使用接口
 * @author YFAN
 * @date 2021/11/17/017
 */
@Service
public class LogOutServiceImpl {
    public void logout() {
        System.out.println("logout() 注销");
    }
}
