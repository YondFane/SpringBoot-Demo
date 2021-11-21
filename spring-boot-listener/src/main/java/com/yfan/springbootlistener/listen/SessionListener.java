package com.yfan.springbootlistener.listen;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/*
 * HttpSessionListener
 * 监听session对象的创建以及销毁
 * @author YFAN
 * @date 2021/11/21/021
 */
@Slf4j
public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        log.info("创建session:{}",session.getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        log.info("销毁session:{}",session.getId());
    }
}
