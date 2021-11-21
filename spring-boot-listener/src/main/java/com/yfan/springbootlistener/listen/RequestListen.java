package com.yfan.springbootlistener.listen;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

@Slf4j
@WebListener
public class RequestListen implements ServletRequestListener {
    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        log.info("RequestListen监听器销毁");
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        // 设置属性
        ServletRequest request = sre.getServletRequest();
        request.setAttribute("ACCESS", "true");
        log.info("RequestListen监听器创建");
    }
}
