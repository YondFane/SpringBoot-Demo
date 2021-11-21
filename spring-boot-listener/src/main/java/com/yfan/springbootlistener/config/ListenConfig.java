package com.yfan.springbootlistener.config;

import com.yfan.springbootlistener.listen.SessionListener;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
 * 监听器配置
 * @author YFAN
 * @date 2021/11/21/021
 */
@Configuration
public class ListenConfig {
    /*
     * 注册监听器
     * @author YFAN
     * @date 2021/11/21/021
     */
    @Bean
    public ServletListenerRegistrationBean sessionListener() {
        ServletListenerRegistrationBean servletListenerRegistrationBean = new ServletListenerRegistrationBean();
        servletListenerRegistrationBean.setListener(new SessionListener());
        return  servletListenerRegistrationBean;
    }
}
