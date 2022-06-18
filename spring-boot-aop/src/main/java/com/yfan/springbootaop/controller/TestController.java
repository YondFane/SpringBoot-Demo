package com.yfan.springbootaop.controller;

import com.yfan.springbootaop.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.concurrent.ExecutionException;

@Controller
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/test")
    public String test() throws ExecutionException, InterruptedException {
        testService.test();
        return "123";
    }

    @GetMapping("/test2")
    public String test2() throws InterruptedException {
        Date now = new Date();
        System.out.println(Thread.currentThread().getName() + "--test2-" + now.toString());
        Thread.sleep(3000);
        System.out.println(Thread.currentThread().getName() + "---" + "test2");
        return now.toString() + ":test2:" + new Date().toString();
    }

    @GetMapping("/test3")
    public String test3() throws InterruptedException {
        Date now = new Date();
        System.out.println(Thread.currentThread().getName() + "--test3-" + now.toString());
        Thread.sleep(3000);
        System.out.println(Thread.currentThread().getName() + "---" + "test3");
        return now.toString() + ":test3:" + new Date().toString();
    }

    @GetMapping("/test4")
    public String test4() throws InterruptedException {
        return "test4";
    }

}
