package com.yfan.springbootlistener.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import java.io.IOException;

/*
 * 空过滤器
 * 什么也不做
 * @author YFAN
 * @date 2021/11/21/021
 */
@Slf4j
public class NullFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("NullFilter过滤器执行------");
        chain.doFilter(request, response);
    }
}
