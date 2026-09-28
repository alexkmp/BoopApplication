package com.boop.service.marketplace.event;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class PetServiceMarketplaceEventProducer {

    private Logger log = LoggerFactory.getLogger(PetServiceMarketplaceEventProducer.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String topicKey, String topicName, String message) {
        log.info("calling PetServiceMarketplaceEventProducer.sendMessage: {}:{}, {}", topicName, topicKey, message);
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, topicKey, message);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Sent message={} with offset={}", message, result.getRecordMetadata().offset());
            } else {
                log.error("Unable to send message={} due to: {}", message, ex.getMessage());
            }
        });
    }
}
