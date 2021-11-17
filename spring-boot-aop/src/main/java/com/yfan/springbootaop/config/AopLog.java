package com.yfan.springbootaop.config;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j//lombok
public class AopLog {

    /*
     * 配置切点
     * @author YFAN
     * @date 2021/11/17/017
     */
    //com.yfan.springbootaop.service.impl包下所有类名以ServiceImpl结尾的类所有方法
    @Pointcut("execution(* com.yfan.springbootaop.service.impl.*ServiceImpl.*(..))")
    public void test(){}

    /**
     * 前置通知,在一个方法执行前被调用。
     */
    @Before("test()")//test()切点
    public void before() {
        log.info("前置通知......");
    }
    /**
     * 环绕通知,在方法执行之前和之后调用的通知。
     */
    @Around("test()")
    public Object around(ProceedingJoinPoint joinpoint) {
        log.info("环绕通知上......");
        Object o=null;
        try {
            // 代理类方法执行
            o=joinpoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        log.info("环绕通知下......");
        return o;
    }
    /**
     * 返回通知,仅当方法成功完成后执行的通知。
     */
    @AfterReturning(pointcut="test()",returning="obj")//切入点，连接点的返回值
    public void afterReturning(JoinPoint joinpoint, Object obj){
        log.info("返回通知......");
    }
    /**
     * 异常通知,在方法抛出异常退出时执行的通知。
     */
    @AfterThrowing(pointcut="test()",throwing="ex")
    public void afterThrowing(JoinPoint joinpoint,Exception ex) {
        log.info("异常通知......");
    }
    /**
     * 后置通知,在方法执行之后调用的通知，无论方法执行是否成功。
     */
    @After("test()")
    public void after() {
        log.info("后置通知......");
    }
}
