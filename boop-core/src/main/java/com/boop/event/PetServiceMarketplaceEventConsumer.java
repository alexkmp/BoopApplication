package com.boop.event;

import org.springframework.kafka.annotation.KafkaListener;

public interface PetServiceMarketplaceEventConsumer {

    @KafkaListener(topics = "service-marketplace-topic", groupId = "test")
    void listenGroupTest(String message);

    @KafkaListener(topics = "service-marketplace-topic", groupId = "created")
    void listenGroupCreated(String message);

    @KafkaListener(topics = "service-marketplace-topic", groupId = "updated")
    void listenGroupUpdated(String message);

    @KafkaListener(topics = "service-marketplace-topic", groupId = "deleted")
    void listenGroupDeleted(String message);
}
