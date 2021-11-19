package com.yfan.springbootfilter.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(filterName = "AccessFilter2")
@Slf4j
public class Access2Filter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("AccessFilter2过滤器执行---------------");
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

    @Override
    public void destroy() {

    }
}
