# Spring Boot实现监听器

## 实现方式一

### 1、实现监听器接口

```java
public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        log.info("创建session:{}",session.getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        log.info("销毁session:{}",session.getId());
    }
}
```

### 2、注册监听器

```java
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
```

## 实现方式二

### 1、@WebListener标注监听类

```java
@WebListener
@Slf4j
public class ServletListen implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("ServletContext创建----------");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("ServletContext销毁----------");
    }
}
```

## 2、@ServletComponentScan监听器扫描包

```java
@ServletComponentScan("com.yfan.springbootlistener.listen")
public class SpringBootListenerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootListenerApplication.class, args);
    }
}

```


## 常用的监听器接口
### 1、ServletContextListener -- 监听servletContext对象的创建以及销毁

```java
1.1    contextInitialized(ServletContextEvent arg0)   -- 创建时执行

1.2    contextDestroyed(ServletContextEvent arg0)  -- 销毁时执行
```

### 2、HttpSessionListener  -- 监听session对象的创建以及销毁

```java
2.2   sessionCreated(HttpSessionEvent se)   -- 创建时执行

2.2   sessionDestroyed(HttpSessionEvent se) -- 销毁时执行
```

### 3、ServletRequestListener -- 监听request对象的创建以及销毁

    3.1    requestInitialized(ServletRequestEvent sre) -- 创建时执行
    
    3.2    requestDestroyed(ServletRequestEvent sre) -- 销毁时执行

### 4、ServletContextAttributeListener  -- 监听servletContext对象中属性的改变

    4.1    attributeAdded(ServletContextAttributeEvent event) -- 添加属性时执行
    
    4.2    attributeReplaced(ServletContextAttributeEvent event) -- 修改属性时执行
    
    4.3    attributeRemoved(ServletContextAttributeEvent event) -- 删除属性时执行

### 5、HttpSessionAttributeListener  --监听session对象中属性的改变

    5.1    attributeAdded(HttpSessionBindingEvent event) -- 添加属性时执行
    
    5.2    attributeReplaced(HttpSessionBindingEvent event) -- 修改属性时执行
    
    5.3    attributeRemoved(HttpSessionBindingEvent event) -- 删除属性时执行

### 6、ServletRequestAttributeListener  --监听request对象中属性的改变

    6.1    attributeAdded(ServletRequestAttributeEvent srae) -- 添加属性时执行
    
    6.2    attributeReplaced(ServletRequestAttributeEvent srae) -- 修改属性时执行
    
    6.3    attributeRemoved(ServletRequestAttributeEvent srae) -- 删除属性时执行


#### 测试

##### http://localhost:8080/listener/index