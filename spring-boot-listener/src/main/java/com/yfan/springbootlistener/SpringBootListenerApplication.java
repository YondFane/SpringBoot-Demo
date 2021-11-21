package com.yfan.springbootlistener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
// 包扫描
@ServletComponentScan("com.yfan.springbootlistener.listen")
public class SpringBootListenerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootListenerApplication.class, args);
    }
}
