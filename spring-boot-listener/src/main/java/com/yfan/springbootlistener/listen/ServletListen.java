package com.yfan.springbootlistener.listen;

import com.yfan.springbootlistener.filter.NullFilter;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/*
 * ServletContextListener
 * 监听servletContext对象的创建以及销毁
 * @author YFAN
 * @date 2021/11/21/021
 */
@WebListener
@Slf4j
public class ServletListen implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        log.info("ServletContext创建----------");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("ServletContext销毁----------");
    }
}
