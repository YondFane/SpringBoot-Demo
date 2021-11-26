# Spring Boot启动原理

**Spring Boot版本为2.6.0**

## 一、从main函数开始

```java
public static void main(String[] args) {
    SpringApplication.run(SpringBootDemoApplication.class, args);
}
```

**SpringApplication的静态run方法**

```java
public static ConfigurableApplicationContext run(Class<?> primarySource, String... args) {
   return run(new Class<?>[] { primarySource }, args);
}

public static ConfigurableApplicationContext run(Class<?>[] primarySources, String[] args) {
    // 新建一个SpringApplicaton实例，先执行构造函数然后在执行非静态的run方法。
   return new SpringApplication(primarySources).run(args);
}
```

**新建一个SpringApplicaton实例，先执行构造函数，然后在执行非静态的run方法。**

## 二、SpringApplication的构造方法

**SpringApplication的构造方法进行的一系列操作**

- 设置类装载器
- 设置引导程序注册的初始化器
- 设置容器的初始化类器
- 设置监听器类实例

```java

public SpringApplication(Class<?>... primarySources) {
		this(null, primarySources);
}

public SpringApplication(ResourceLoader resourceLoader, Class<?>... primarySources) {
   this.resourceLoader = resourceLoader;
   Assert.notNull(primarySources, "PrimarySources must not be null");
   this.primarySources = new LinkedHashSet<>(Arrays.asList(primarySources));
   /**
    * web应用程序类型(三种类型--枚举)
    * NONE	应用程序不应该作为web应用程序运行，也不应该启动嵌入式web服务器。   	     	
    * SERVLET	应用程序应该作为基于servlet的web应用程序运行，并且应该启动一个嵌入式servlet web服务器。  
    * REACTIVE	应用程序应该作为响应式web应用程序运行，并且应该启动一个嵌入式响应式web服务器。	  
    */
   this.webApplicationType = WebApplicationType.deduceFromClasspath();
   /**
    * getSpringFactoriesInstances(BootstrapRegistryInitializer.class)方法
    * 读取spring-boot-xxx-2.6.0.jar下的META-INF下的spring.factories文件,不止一个spring.factories文件
    * spring.factories文件配置了对应接口的实现类的全路径名
    * 通过反射构建对应class实例返回，返回的是一个集合
    * 例如 如果传入ApplicationContextInitializer.class,
    * 那么就返回配置了org.springframework.context.ApplicationContextInitializer=xxx.xxx,/xxx.xxx,xxx.xxx
    * 的所有ApplicationContextInitializer接口实现类的实例集合
    *
    * 传参BootstrapRegistryInitializer.class
    * 设置引导程序注册的初始化器
    */
   this.bootstrapRegistryInitializers = new ArrayList<>(
         getSpringFactoriesInstances(BootstrapRegistryInitializer.class));
   /**
    * getSpringFactoriesInstances(BootstrapRegistryInitializer.class)方法
    * 传参ApplicationContextInitializer.class
    * org.springframework.context.ApplicationContextInitializer接口
    * 设置容器的初始化器类实例
    */ 
   setInitializers((Collection) getSpringFactoriesInstances(ApplicationContextInitializer.class));
   /**
    * getSpringFactoriesInstances(BootstrapRegistryInitializer.class)方法
    * 传参ApplicationListener.class
    * org.springframework.context.ApplicationListener接口
    * 设置监听器类实例
    */
   setListeners((Collection) getSpringFactoriesInstances(ApplicationListener.class));
   // 根据Java虚拟机栈的入栈的方法寻找main方法，返回main方法的class对象
   this.mainApplicationClass = deduceMainApplicationClass();
}
```

**spring.factories文件内容**（spring-boot-2.6.0.jar包META-INF目录下的，其他spring-boot-xxx.jar包也有）

```properties
# Logging Systems
org.springframework.boot.logging.LoggingSystemFactory=\
org.springframework.boot.logging.logback.LogbackLoggingSystem.Factory,\
org.springframework.boot.logging.log4j2.Log4J2LoggingSystem.Factory,\
org.springframework.boot.logging.java.JavaLoggingSystem.Factory

# PropertySource Loaders
org.springframework.boot.env.PropertySourceLoader=\
org.springframework.boot.env.PropertiesPropertySourceLoader,\
org.springframework.boot.env.YamlPropertySourceLoader

# ConfigData Location Resolvers
org.springframework.boot.context.config.ConfigDataLocationResolver=\
org.springframework.boot.context.config.ConfigTreeConfigDataLocationResolver,\
org.springframework.boot.context.config.StandardConfigDataLocationResolver

# ConfigData Loaders
org.springframework.boot.context.config.ConfigDataLoader=\
org.springframework.boot.context.config.ConfigTreeConfigDataLoader,\
org.springframework.boot.context.config.StandardConfigDataLoader

# Run Listeners
org.springframework.boot.SpringApplicationRunListener=\
org.springframework.boot.context.event.EventPublishingRunListener

# Error Reporters
org.springframework.boot.SpringBootExceptionReporter=\
org.springframework.boot.diagnostics.FailureAnalyzers

# Application Context Initializers
org.springframework.context.ApplicationContextInitializer=\
org.springframework.boot.context.ConfigurationWarningsApplicationContextInitializer,\
org.springframework.boot.context.ContextIdApplicationContextInitializer,\
org.springframework.boot.context.config.DelegatingApplicationContextInitializer,\
org.springframework.boot.rsocket.context.RSocketPortInfoApplicationContextInitializer,\
org.springframework.boot.web.context.ServerPortInfoApplicationContextInitializer

# Application Listeners
org.springframework.context.ApplicationListener=\
org.springframework.boot.ClearCachesApplicationListener,\
org.springframework.boot.builder.ParentContextCloserApplicationListener,\
org.springframework.boot.context.FileEncodingApplicationListener,\
org.springframework.boot.context.config.AnsiOutputApplicationListener,\
org.springframework.boot.context.config.DelegatingApplicationListener,\
org.springframework.boot.context.logging.LoggingApplicationListener,\
org.springframework.boot.env.EnvironmentPostProcessorApplicationListener

# Environment Post Processors
org.springframework.boot.env.EnvironmentPostProcessor=\
org.springframework.boot.cloud.CloudFoundryVcapEnvironmentPostProcessor,\
org.springframework.boot.context.config.ConfigDataEnvironmentPostProcessor,\
org.springframework.boot.env.RandomValuePropertySourceEnvironmentPostProcessor,\
org.springframework.boot.env.SpringApplicationJsonEnvironmentPostProcessor,\
org.springframework.boot.env.SystemEnvironmentPropertySourceEnvironmentPostProcessor,\
org.springframework.boot.reactor.DebugAgentEnvironmentPostProcessor

# Failure Analyzers
org.springframework.boot.diagnostics.FailureAnalyzer=\
org.springframework.boot.context.config.ConfigDataNotFoundFailureAnalyzer,\
org.springframework.boot.context.properties.IncompatibleConfigurationFailureAnalyzer,\
org.springframework.boot.context.properties.NotConstructorBoundInjectionFailureAnalyzer,\
org.springframework.boot.diagnostics.analyzer.BeanCurrentlyInCreationFailureAnalyzer,\
org.springframework.boot.diagnostics.analyzer.BeanDefinitionOverrideFailureAnalyzer,\
org.springframework.boot.diagnostics.analyzer.BeanNotOfRequiredTypeFailureAnalyzer,\
org.springframework.boot.diagnostics.analyzer.BindFailureAnalyzer,\
org.springframework.boot.diagnostics.analyzer.BindValidationFailureAnalyzer,\
org.springframework.boot.diagnostics.analyzer.UnboundConfigurationPropertyFailureAnalyzer,\
org.springframework.boot.diagnostics.analyzer.ConnectorStartFailureAnalyzer,\
org.springframework.boot.diagnostics.analyzer.MutuallyExclusiveConfigurationPropertiesFailureAnalyzer,\
org.springframework.boot.diagnostics.analyzer.NoSuchMethodFailureAnalyzer,\
org.springframework.boot.diagnostics.analyzer.NoUniqueBeanDefinitionFailureAnalyzer,\
org.springframework.boot.diagnostics.analyzer.PortInUseFailureAnalyzer,\
org.springframework.boot.diagnostics.analyzer.ValidationExceptionFailureAnalyzer,\
org.springframework.boot.diagnostics.analyzer.InvalidConfigurationPropertyNameFailureAnalyzer,\
org.springframework.boot.diagnostics.analyzer.InvalidConfigurationPropertyValueFailureAnalyzer,\
org.springframework.boot.diagnostics.analyzer.PatternParseFailureAnalyzer,\
org.springframework.boot.liquibase.LiquibaseChangelogMissingFailureAnalyzer

# Failure Analysis Reporters
org.springframework.boot.diagnostics.FailureAnalysisReporter=\
org.springframework.boot.diagnostics.LoggingFailureAnalysisReporter

# Database Initializer Detectors
org.springframework.boot.sql.init.dependency.DatabaseInitializerDetector=\
org.springframework.boot.flyway.FlywayDatabaseInitializerDetector,\
org.springframework.boot.jdbc.AbstractDataSourceInitializerDatabaseInitializerDetector,\
org.springframework.boot.jdbc.init.DataSourceScriptDatabaseInitializerDetector,\
org.springframework.boot.liquibase.LiquibaseDatabaseInitializerDetector,\
org.springframework.boot.orm.jpa.JpaDatabaseInitializerDetector,\
org.springframework.boot.r2dbc.init.R2dbcScriptDatabaseInitializerDetector

# Depends On Database Initialization Detectors
org.springframework.boot.sql.init.dependency.DependsOnDatabaseInitializationDetector=\
org.springframework.boot.sql.init.dependency.AnnotationDependsOnDatabaseInitializationDetector,\
org.springframework.boot.jdbc.SpringJdbcDependsOnDatabaseInitializationDetector,\
org.springframework.boot.jooq.JooqDependsOnDatabaseInitializationDetector,\
org.springframework.boot.orm.jpa.JpaDependsOnDatabaseInitializationDetector

```

## 三、SpringApplication实例的run()方法（非静态run方法）



```java
public ConfigurableApplicationContext run(String... args) {
   // 开始计时：返回正在运行的Java虚拟机的当前值 
   long startTime = System.nanoTime();
   /**
   	* createBootstrapContext()
   	* 引导程序注册的初始化器执行初始化方法
   	* 创建引导加载程序上下文，引导上下文
   	* ’引导上下文‘为‘应用上下文’的父级
   	*/
   DefaultBootstrapContext bootstrapContext = createBootstrapContext();
   ConfigurableApplicationContext context = null;
   /**
    * 设置了一个名为java.awt.headless的系统属性
    * java.awt.headless是J2SE的一种模式用于在缺少显示屏、键盘或者鼠标时的系统配置
    * 大概是设置该应用程序,即使没有检测到显示器,也允许其启动
    */
   configureHeadlessProperty();
   /**
    * 获取spring.factories文件配置org.springframework.boot.SpringApplicationRunListener接口的实现类
    * 新建SpringApplicationRunListeners实例
    * 方便进行管理SpringApplicationRunListener监听器们
    * SpringApplicationRunListeners实例中的listenrs成员变量存储监听器实例
    * private final List<SpringApplicationRunListener> listeners
    */
   SpringApplicationRunListeners listeners = getRunListeners(args);
   /**
    * SpringApplicationRunListeners实例listeners中的所有监听器实例开始工作
    */
   listeners.starting(bootstrapContext, this.mainApplicationClass);
   try {
      /**
       * 应用参数信息
       */ 
      ApplicationArguments applicationArguments = new DefaultApplicationArguments(args);
      /**
       * 根据web应用程序类型（NONE、SERVLET、REACTIVE）准备配置环境
       * webApplication为SERVLET,返回了StandardServletEnvironment实例
       * 为当前应用绑定环境
       */ 
      ConfigurableEnvironment environment = prepareEnvironment(listeners, bootstrapContext, applicationArguments);
      /**
       * 设置spring.beaninfo.ignore系统属性
       * spring.beaninfo.ignore其值为“true”意味着跳过对BeanInfo类的搜索(通常用于应用程序中首先没有为bean定义此类类的场景)。  
	   * 默认值为“false”，考虑到所有BeanInfo元数据类，比如标准的Introspector.getBeanInfo(Class)调用。  
	   * 如果您对不存在的BeanInfo类重复进行ClassLoader访问，那么考虑将此标志切换为“true”，以防止在启动或延迟加载时这种访问开销很大。
       */
      configureIgnoreBeanInfo(environment);
      /**
       * 控制台输出Spring的字符集以及版本号
       * 如果想自定义启动横幅广告可以创建类实现Banner接口的printBanner()方法。
       * spring.banner.location 设置横幅广告自定义类
       * spring.banner.image.location 设置横幅广告图片自定义类
                 .   ____          _            __ _ _
         /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
        ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
         \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
          '  |____| .__|_| |_|_| |_\__, | / / / /
         =========|_|==============|___/=/_/_/_/
         :: Spring Boot ::                (v2.6.0)
       */
      Banner printedBanner = printBanner(environment);
      /**
       * 创建应用上下文
       * 通过ApplicationContextFactory接口的默认方法创建应用上下文
       * SERVLET	new AnnotationConfigServletWebServerApplicationContext()
       * REACTIVE new AnnotationConfigReactiveWebServerApplicationContext()
       * NONE	new AnnotationConfigApplicationContext()
       * 应用上下文的构造器中主要做了两件事
       * 以new AnnotationConfigServletWebServerApplicationContext()为例
       * public AnnotationConfigServletWebServerApplicationContext() {
       *	// 初始化注解bean定义读取器
	   *	this.reader = new AnnotatedBeanDefinitionReader(this);
	   *	// 初始化注解bean定义读取器
	   *	this.scanner = new ClassPathBeanDefinitionScanner(this);
		}
       */
      context = createApplicationContext();
      /**
       * 设置应用程序启动阶段  
	   * 核心容器及其基础设施组件可以使用ApplicationStartup 
	   * 在应用程序启动期间标记步骤，并收集关于执行上下文的数据或处理时间。  
       */
      context.setApplicationStartup(this.applicationStartup);
      /**
       * 准备加载应用上下文
       */
      prepareContext(bootstrapContext, context, environment, listeners, applicationArguments, printedBanner);
      /**
       * 刷新应用上下文
       */
      refreshContext(context);
      /**
       * 刷新应用上下文之后
       */
      afterRefresh(context, applicationArguments);
      Duration timeTakenToStartup = Duration.ofNanos(System.nanoTime() - startTime);
      if (this.logStartupInfo) {
         new StartupInfoLogger(this.mainApplicationClass).logStarted(getApplicationLog(), timeTakenToStartup);
      }
      // 监听器启动
      listeners.started(context, timeTakenToStartup);
      callRunners(context, applicationArguments);
   }
   catch (Throwable ex) {
      handleRunFailure(context, ex, listeners);
      throw new IllegalStateException(ex);
   }
   try {
      Duration timeTakenToReady = Duration.ofNanos(System.nanoTime() - startTime);
      // 监听器开始工作
      listeners.ready(context, timeTakenToReady);
   }
   catch (Throwable ex) {
      handleRunFailure(context, ex, null);
      throw new IllegalStateException(ex);
   }
   return context;
}
```

## 四、SpringApplication实例的prepareContext()方法



```java
private void prepareContext(DefaultBootstrapContext bootstrapContext, ConfigurableApplicationContext context,
      ConfigurableEnvironment environment, SpringApplicationRunListeners listeners,
      ApplicationArguments applicationArguments, Banner printedBanner) {
   // 设置配置环境
   context.setEnvironment(environment);
   // 后置处理应用上下文
   postProcessApplicationContext(context);
   /**
   	* 在刷新上下文之前应用任何ApplicationContextInitializer
   	* 执行所有ApplicationContextInitializer实现类的initialize方法
   	*/
   applyInitializers(context);
   // 会按优先级顺序遍历执行SpringApplicationRunListener的contextPrepared方法
   listeners.contextPrepared(context);
   // 关闭bootstrapContext
   bootstrapContext.close(context);
   if (this.logStartupInfo) {
      // 启动信息输出到控制台
      logStartupInfo(context.getParent() == null);
      // 活动配置文件信息输出到控制台
      logStartupProfileInfo(context);
   }
   // Add boot specific singleton beans
   // 获取应用上下文的内部bean工厂
   ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
   // applicationArguments注册为单例，bean的ID为springApplicationArguments
   beanFactory.registerSingleton("springApplicationArguments", applicationArguments);
   if (printedBanner != null) {
      // printedBanner注册为单例，bean的ID为springBootBanner
      beanFactory.registerSingleton("springBootBanner", printedBanner);
   }
   if (beanFactory instanceof AbstractAutowireCapableBeanFactory) {
      ((AbstractAutowireCapableBeanFactory) beanFactory).setAllowCircularReferences(this.allowCircularReferences);
      if (beanFactory instanceof DefaultListableBeanFactory) {
          // 设置bean定义信息是否允许覆盖，this.allowBeanDefinitionOverriding默认为false
         ((DefaultListableBeanFactory) beanFactory)
               .setAllowBeanDefinitionOverriding(this.allowBeanDefinitionOverriding);
      }
   }
   // 根据this.lazyInitialization布尔值，是否添加懒加载bean工厂后置处理器
   if (this.lazyInitialization) {
      context.addBeanFactoryPostProcessor(new LazyInitializationBeanFactoryPostProcessor());
   }
   // Load the sources
   // 获取bean，为SpringApplication.run(SpringBootShiroApplication.class, args)中这里传的class
   Set<Object> sources = getAllSources();
   Assert.notEmpty(sources, "Sources must not be empty");
   // 为容器加载bean
   load(context, sources.toArray(new Object[0]));
   // 通知监听器们，容器加载完成。
   // 在加载应用程序上下文之后，但在刷新之前调用它。 
   listeners.contextLoaded(context);
}
```

## 五、SpringApplication实例的refreshContext()方法

```java
private void refreshContext(ConfigurableApplicationContext context) {
   if (this.registerShutdownHook) {
      // 一个Runnable，用作一个关闭钩子，用于优雅地关闭Spring Boot应用程序
      shutdownHook.registerApplicationContext(context);
   }
   refresh(context);
}
```

**以下是调用AbstractApplicationContext类的refresh()方法**

```java
public void refresh() throws BeansException, IllegalStateException {
   synchronized (this.startupShutdownMonitor) {
      StartupStep contextRefresh = this.applicationStartup.start("spring.context.refresh");

      /**
       * 刷新准备
       * 为刷新、设置其启动日期和准备该上下文以及执行属性源的任何初始化做准备
       */
      prepareRefresh();

      // 获取刷新后的bean工厂
      ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();

      // 准备bean工厂，配置bean工厂属性
      prepareBeanFactory(beanFactory);

      try {
         // 设置bean工厂的后置处理
         postProcessBeanFactory(beanFactory);
		 // 开始ng.context.beans.post-process步骤
         StartupStep beanPostProcess = this.applicationStartup.start("spring.context.beans.post-process");
         // 调用在上下文中注册为bean工厂后置处理器
         invokeBeanFactoryPostProcessors(beanFactory);

         // 注册拦截bean创建的bean后置处理器
         registerBeanPostProcessors(beanFactory);
         // spring.context.beans.post-process步骤结束
         beanPostProcess.end();

         // 初始化MessageSource，国际化处理
         initMessageSource();

         // 初始化事件
         initApplicationEventMulticaster();

         // Initialize other special beans in specific context subclasses.
         onRefresh();

         // 注册监听器们
         registerListeners();

         // 完成此上下文的bean工厂的初始化，初始化所有剩余的单例bean。  
         finishBeanFactoryInitialization(beanFactory);

         /**
          * 完成刷新
          * 
          * 清除上下文级资源缓存
          * 初始化此上下文的生命周期处理器
          * 首先将刷新传播到生命周期处理器
          * 发布事件
          */
         finishRefresh();
      }

      catch (BeansException ex) {
         if (logger.isWarnEnabled()) {
            logger.warn("Exception encountered during context initialization - " +
                  "cancelling refresh attempt: " + ex);
         }

         // Destroy already created singletons to avoid dangling resources.
         destroyBeans();

         // Reset 'active' flag.
         cancelRefresh(ex);

         // Propagate exception to caller.
         throw ex;
      }

      finally {
         // Reset common introspection caches in Spring's core, since we
         // might not ever need metadata for singleton beans anymore...
         resetCommonCaches();// 重置缓存
         contextRefresh.end();
      }
   }
}
```
