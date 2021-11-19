package com.yfan.springbootfilter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan("com.yfan.springbootfilter.filter")
public class SrpingBootFilterApplication {

    public static void main(String[] args) {
        SpringApplication.run(SrpingBootFilterApplication.class, args);
    }

}
