package com.yfan.springboothandlerinterceptor.config;

import com.yfan.springboothandlerinterceptor.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
 * 配置类
 * @author YFAN
 * @date 2021/11/20/020
 */
@Configuration
public class LoginConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器
        InterceptorRegistration registration = registry.addInterceptor(new LoginInterceptor());
        // 设置拦截规则 /**所有路径都拦截
        registration.addPathPatterns("/**");
        // 拦截白名单
        registration.excludePathPatterns("/index","/**/*.html","/**/*.js","/**/*.css");
    }
}
