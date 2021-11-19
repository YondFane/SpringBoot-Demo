# Spring Boot Filter过滤器的实现

**Fileter指的是javax.servlet.Filter**

## 第一种实现

### 1、实现Filter的方法

```Java
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
```

### 2、配置类注册Filter

```java
@Configuration
public class FilterConfig {
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
```



## 第二种实现

### 1、SpringBootApplication 上使用**@ServletComponentScan** 注解后

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan("com.yfan.springbootfilter.filer")
public class SrpingBootFilterApplication {

    public static void main(String[] args) {
        SpringApplication.run(SrpingBootFilterApplication.class, args);
    }

}
```

**@ServletComponentScan注解**

Servlet可以直接通过@WebServlet注解自动注册
Filter可以直接通过@WebFilter注解自动注册
Listener可以直接通过@WebListener 注解自动注册

### 2、在Filter的实现类上加上@WebFilter

```java
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
```

