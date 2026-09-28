package com.boop.service.marketplace.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PetSpecialistEventConsumer {

    private Logger log = LoggerFactory.getLogger(PetSpecialistEventConsumer.class);

    @KafkaListener(topics = "specialist-topic", groupId = "byLogin")
    public void listenGroupByLogin(String message) {
        log.info("Received message in topic: specialist-topic, group: byLogin, message: {}", message);
    }

    @KafkaListener(topics = "specialist-topic", groupId = "updated")
    public void listenGroupUpdated(String message) {
        log.info("Received message in topic: specialist-topic, group: updated, message: {}", message);
    }

    @KafkaListener(topics = "specialist-topic", groupId = "deleted")
    public void listenGroupDeleted(String message) {
        log.info("Received message in topic: specialist-topic, group: deleted, message: {}", message);
    }
}
