package com.yfan.springbootfilter.config;

import com.yfan.springbootfilter.filter.AccessFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
* 过滤器配置
* @Author YFAN
* @Date 2021/11/19
*/
@Configuration
public class FilterConfig {

    /**
    * 注册过滤器
    * @Author YFAN
    * @Date 2021/11/19
    * @params []
    * @return org.springframework.boot.web.servlet.FilterRegistrationBean
    */
    @Bean
    public FilterRegistrationBean registrationBean(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        // 设置过滤器
        filterRegistrationBean.setFilter(new AccessFilter());
        // url匹配规则
        filterRegistrationBean.addUrlPatterns("/*");
        // 过滤器名称
        filterRegistrationBean.setName("AccessFilter");
        // 过滤器排序
        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;
    }
}
