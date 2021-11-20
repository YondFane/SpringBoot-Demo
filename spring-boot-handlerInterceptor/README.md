# Spring Boot实现拦截器

## 拦截器应用场景

拦截器本质上是面向切面编程（AOP），符合横切关注点的功能都可以放在拦截器中来实现，主要的应用场景包括：

- 登录验证，判断用户是否登录。
- 权限验证，判断用户是否有权限访问资源，如校验token
- 日志记录，记录请求操作日志（用户ip，访问时间等），以便统计请求访问量。
- 处理cookie、本地化、国际化、主题等。
- 性能监控，监控请求处理时长等。
- 通用行为：读取cookie得到用户信息并将用户对象放入请求，从而方便后续流程使用，还有如提取Locale、Theme信息等，只要是多个处理器都需要的即可使用拦截器实现）

## 实现原理

### 1、实现HandleInterceptor接口

实现HandlerInterceptor接口需要实现3个方法：`preHandle`、`postHandle`、`afterCompletion`

```java
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
            return true;
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
```

### 2、实现WebMvcConfigurer接口，注册拦截器

```java
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
```


### 测试
http://localhost:8080/interceptor/index

------

------

------

## 过滤器和拦截器的区别

1、过滤器和拦截器**触发时机不一样**，**过滤器是**在请**求进入容器后**，但请求**进入servlet之前**进**行预处理**的。请求结束返回也是，是在servlet处理完后，返回给前端之前。

2、**拦截器**可以获取IOC容器中的各个bean，而过滤器就不行，因为拦**截器是spring提供并管理的**，spring的功能可以被拦截器使用，在拦截器里注入一个service，可以调用业务逻辑。而过滤器是JavaEE标准，只需依赖servlet api ，不需要依赖spring。

3、**过滤器的实现**基于**回调函数**。而**拦截器**（代理模式）的实现**基于反射**

4、**Filter**是依**赖于Servlet容**器，**属于Servlet规范的一部分**，而**拦截器则是独立存**在的，可以在任何情况下使用。

5、**Filte**r的执行由**Servlet容器回调完成**，而**拦截器**通常通**过动态代理（反射）**的方式来执行。

6、**Filter的生命周**期**由Servlet容器管理**，而**拦截器则**可以通过I**oC容器来管理**，因此可以通过注入等方式来获取其他Bean的实例，因此使用会更方便。

**简单明了：**

**过滤器可以修改request，而拦截器不能**
**过滤器需要在servlet容器中实现，拦截器可以适用于javaEE，javaSE等各种环境**
**拦截器可以调用IOC容器中的各种依赖，而过滤器不能**
**过滤器只能在请求的前后使用，而拦截器可以详细到每个方法**

#### [Springboot过滤器和拦截器详解及使用场景 - 知乎 (zhihu.com)](https://zhuanlan.zhihu.com/p/340397290)

