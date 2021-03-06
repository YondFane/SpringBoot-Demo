package com.yfan.springbootkafka.handle;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/*
 * 消息处理器
 * @author YFAN
 * @date 2021/11/28/028
 */
@Slf4j
@Component
public class MessageHandler {

    @KafkaListener(topics = "TEST", groupId = "GROUP", containerFactory = "ackContainerFactory")
//    @KafkaListener(topicPartitions = @TopicPartition(topic = "TEST", partitions = {"1"}) ,groupId = "GROUP", containerFactory = "ackContainerFactory")
    public void handleMessager(ConsumerRecord record, Acknowledgment acknowledgment) {
        try {
            int partition = record.partition();
            String key = (String) record.key();
            String message = (String) record.value();
            log.info("分区{}收到的信息为key:{},message:{}", partition, key, message);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            // 提交
            acknowledgment.acknowledge();
        }
    }

}
