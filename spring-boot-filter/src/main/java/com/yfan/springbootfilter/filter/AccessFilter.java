package com.yfan.springbootfilter.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import java.io.IOException;

/**
* 实现javax.servlet.Filter接口
* @Author YFAN
* @Date 2021/11/19
*/
@Slf4j
public class AccessFilter implements Filter {
    // 初始化方法
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
    * 过滤器逻辑
    * @Author YFAN
    * @Date 2021/11/19
    * @params [servletRequest, servletResponse, filterChain]
    * @return void
    */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("AccessFilter过滤器执行---------------");
        /**
         * 判断请求参数中的access是否为true
         * 不满足则过滤掉
        */
        String access = servletRequest.getParameter("access");
        if (access != null && access.equals("true")) {
            // 执行过滤链
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }

    // 销毁方法
    @Override
    public void destroy() {

    }
}
