package com.yfan.springboothandlerinterceptor.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*
 * 登录拦截器
 * 实现HandlerInterceptor接口
 * @author YFAN
 * @date 2021/11/20/020
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    /*
     * 在请求处理之前进行调用(Controller方法调用之前)
     * 返回值为false请求被拦截
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("执行拦截器的preHandle方法---------");
        String access = request.getParameter("access");
        // access为true放行，否则拦截
        if (access!=null && "true".equals(access)) {
            // 测试修改request的参数
            request.setAttribute("access", "TEST");
            return true;
        } else {
            // 判断session中是否有ACCESS，并且为true
            HttpSession session = request.getSession();
            String ACCESSS = (String) session.getAttribute("ACCESS");
            if ("true".equals(ACCESSS)) {
                return true;
            }
        }
        // 重定向到首页
        response.sendRedirect("index");
        return false;
    }

    /*
     * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("执行拦截器的postHandle方法---------");
    }

    /*
     * 整个请求结束之后被调用，也就是在DispatchServlet渲染了对应的视图之后执行（主要用于进行资源清理工作）
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("执行拦截器的afterCompletion方法---------");
    }
}
