package com.boop.owners.event;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class PetOwnerEventProducer {

    private Logger log = LoggerFactory.getLogger(PetOwnerEventProducer.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(String topicKey, String topicName, Object object) {
        log.info("calling PetOwnerEventProducer.sendMessage: {}:{}, {}", topicName, topicKey, object);
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topicName, topicKey, object);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Sent message={} with offset={}", object, result.getRecordMetadata().offset());
            } else {
                log.error("Unable to send message={} due to: {}", object, ex.getMessage());
            }
        });
    }
}
