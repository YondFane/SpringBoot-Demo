package com.yfan.springbootkafka.controller;

import com.yfan.springbootkafka.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class KafkaController {
    @Autowired
    KafkaService kafkaService;

    @RequestMapping("testMessage")
    @ResponseBody
    public void testMessage() {
        kafkaService.sendMessage("TEST", "测试");
    }

}
