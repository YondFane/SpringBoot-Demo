# Spring Boot集成Shiro

## 一、Shiro简介

### 1、基础概念

Apache Shiro是一个强大且易用的Java安全框架,执行身份验证、授权、密码和会话管理。作为一款安全框架Shiro的设计相当巧妙。Shiro的应用不依赖任何容器，它不仅可以在JavaEE下使用，还可以应用在JavaSE环境中。

### 2、核心角色

1）Subject：认证主体

代表当前系统的使用者，就是用户，在Shiro的认证中，认证主体通常就是userName和passWord，或者其他用户相关的唯一标识。

2）SecurityManager：安全管理器

Shiro架构中最核心的组件，通过它可以协调其他组件完成用户认证和授权。实际上，SecurityManager就是Shiro框架的控制器。

3）Realm：域对象

定义了访问数据的方式，用来连接不同的数据源，如：关系数据库，配置文件等等。

## 3、核心理念

Shiro自己不维护用户和权限，通过Subject用户主体和Realm域对象的注入，完成用户的认证和授权。

## 二、集成实践

### 1、自定义Realm

```java
@Slf4j
public class CustomRealm extends AuthorizingRealm {
    /*
     * 模拟用户密码
     */
    private static HashMap<String, String> USERNAME_PASSWORD = new HashMap<>();

    static {
        USERNAME_PASSWORD.put("JACK", "JACK123456");
        USERNAME_PASSWORD.put("TOM", "TOM_!@#");
        USERNAME_PASSWORD.put("MEIKO", "MEIKOABC");
    }
    /**
     * 权限认证
     * 配合权限注解使用
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> stringSet = new HashSet<>();
        stringSet.add("user:info");
        info.setStringPermissions(stringSet);
        return info;
    }
    /**
     * 权限认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("-------身份认证方法--------");
        String username = (String) authenticationToken.getPrincipal();
        String password = new String((char[]) authenticationToken.getCredentials());
        // 判断用户是否存在
        if (!USERNAME_PASSWORD.containsKey(username)) {
            throw new UnknownAccountException("未知用户");
        }
        // 根据用户名从数据库获取密码
        String correctPassword = USERNAME_PASSWORD.get(username);
        log.info("{}的正确密码：{}", username, correctPassword);
        if (username == null) {
            throw new AccountException("用户名不正确");
        } else if (!password.equals(correctPassword)) {
            throw new IncorrectCredentialsException("密码不正确");
        }
        return new SimpleAuthenticationInfo(username, correctPassword, getName());
    }
}
```

### 2、Shiro配置

```java
@Configuration
public class ShiroConfig {
    /**
    * shiro过滤器
    */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 设置需要登录的路径
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 权限不足跳转的页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/notRole");
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // anon:所有url都都可以匿名访问
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/notRole", "anon");
        filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/front/**", "anon");
        filterChainDefinitionMap.put("/api/**", "anon");
        // authc:所有url都必须认证通过才可以访问
        filterChainDefinitionMap.put("/user/**", "authc");
        //主要这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截 剩余的都需要认证
        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
    * 安全管理
    */
    @Bean
    public SecurityManager  securityManager() {
        DefaultWebSecurityManager defaultSecurityManager = new DefaultWebSecurityManager();
        defaultSecurityManager.setRealm(customRealm());
        return defaultSecurityManager;
    }

    @Bean
    public CustomRealm customRealm() {
        CustomRealm customRealm = new CustomRealm();
        return customRealm;
    }

    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }
}
```



# 测试

http://localhost:8080/shiro/login
