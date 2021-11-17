package com.yfan.springbootaop.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class LoginLogoutControllerTest {
    @Autowired
    LoginLogoutController loginLogoutController;
    @Test
    void login() {
        loginLogoutController.login();
        /*
            2021-11-17 22:53:15,246 [main] INFO  [com.yfan.springbootaop.config.AopLog] AopLog.java:37 - 环绕通知上......
            2021-11-17 22:53:15,246 [main] INFO  [com.yfan.springbootaop.config.AopLog] AopLog.java:30 - 前置通知......
            login() 登录
            2021-11-17 22:53:15,256 [main] INFO  [com.yfan.springbootaop.config.AopLog] AopLog.java:52 - 返回通知......
            2021-11-17 22:53:15,256 [main] INFO  [com.yfan.springbootaop.config.AopLog] AopLog.java:66 - 后置通知......
            2021-11-17 22:53:15,256 [main] INFO  [com.yfan.springbootaop.config.AopLog] AopLog.java:44 - 环绕通知下......
         */
    }

    @Test
    void logout() {
        loginLogoutController.logout();
        /*
            2021-11-17 22:54:23,116 [main] INFO  [com.yfan.springbootaop.config.AopLog] AopLog.java:37 - 环绕通知上......
            2021-11-17 22:54:23,116 [main] INFO  [com.yfan.springbootaop.config.AopLog] AopLog.java:30 - 前置通知......
            logout() 注销
            2021-11-17 22:54:23,126 [main] INFO  [com.yfan.springbootaop.config.AopLog] AopLog.java:52 - 返回通知......
            2021-11-17 22:54:23,126 [main] INFO  [com.yfan.springbootaop.config.AopLog] AopLog.java:66 - 后置通知......
            2021-11-17 22:54:23,126 [main] INFO  [com.yfan.springbootaop.config.AopLog] AopLog.java:44 - 环绕通知下......
         */
    }
}