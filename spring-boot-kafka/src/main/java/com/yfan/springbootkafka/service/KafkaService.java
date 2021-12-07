package com.yfan.springbootkafka.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
@Slf4j
public class KafkaService {
    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage( String message) {
        String topic = "TEST";
        log.info("开始发送消息：topic：{}，message：{}", topic, message);
        // 将消息发送到主题TEST中
        ListenableFuture<SendResult<String, String>> send = kafkaTemplate.send(topic,"MSG", message);
        send.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("send error,ex:{},topic:{},msg:{}", ex, topic, message);
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("send success,topic:{},msg:{}", topic, message);
            }
        });
    }
}
