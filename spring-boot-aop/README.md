# Spring Boot集成AOP

## AOP中的相关概念

- **Aspect（切面）**： Aspect 声明类似于 Java 中的类声明，在 Aspect 中会包含着一些 Pointcut 以及相应的 Advice。
- **Joint point（连接点）**：表示在程序中明确定义的点，典型的包括方法调用，对类成员的访问以及异常处理程序块的执行等等，它自身还可以嵌套其它 joint point。
- **Pointcut（切点）**：表示一组 joint point，这些 joint point 或是通过逻辑关系组合起来，或是通过通配、正则表达式等方式集中起来，它定义了相应的 Advice 将要发生的地方。
- **Advice（增强）**：Advice 定义了在 Pointcut 里面定义的程序点具体要做的操作，它通过 before、after 和 around 来区别是在每个 joint point 之前、之后还是代替执行的代码。
- **Target（目标对象）**：织入 Advice 的目标对象。
- **Weaving（织入）**：将 Aspect 和其他对象连接起来, 并创建 Adviced object 的过程。

## Advice 的类型

- **before advice**, 在 join point 前被执行的 advice. 虽然 before advice 是在 join point 前被执行, 但是它并不能够阻止 join point 的执行, 除非发生了异常(即我们在 before advice 代码中, 不能人为地决定是否继续执行 join point 中的代码)。
- **after return advice**, 在一个 join point 正常返回后执行的 advice。
- **after throwing advice**, 当一个 join point 抛出异常后执行的 advice。
- **after(final) advice**, 无论一个 join point 是正常退出还是发生了异常, 都会被执行的 advice。
- **around advice**, 在 join point 前和 joint point 退出后都执行的 advice. 这个是最常用的 advice。
- **introduction**，introduction可以为原有的对象增加新的属性和方法。

## AOP注解的实现

1. **类上使用@Aspect注解标注。**
2. **通过@Pointcut注解标注在方法上面，用来定义切入点。**
3. **使用@Before标注在方法上面，定义了一个前置通知，通过value引用了上面已经定义的切入点，表示这个通知会对Service1中的所有方法生效，在通知中可以通过这个类名.方法名()引用@Pointcut定义的切入点，表示这个通知对这些切入点有效，若@Before和@Pointcut在一个类的时候，直接通过方法名()引用当前类中定义的切入点。**
4. **这个使用@AfterThrowing定义了一个异常通知，也是对通过value引用了上面已经定义的切入点，表示这个通知会对Service1中的所有方法生效，若Service1中的方法抛出了Exception类型的异常，都会回调afterThrowing方法。**



## 注意

**Spring Boot2.X以后，默认使用的是CGLIB代理，除非spring.aop.proxy-target-class设置为false。**