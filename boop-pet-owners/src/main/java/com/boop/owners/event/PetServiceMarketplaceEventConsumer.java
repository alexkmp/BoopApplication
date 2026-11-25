package com.boop.owners.event;

import com.boop.event.marketplace.ServiceClaimCreated;
import com.boop.event.marketplace.ServiceClaimDeleted;
import com.boop.event.marketplace.ServiceClaimUpdated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "service-marketplace-topic", groupId = "pet-owners")
public class PetServiceMarketplaceEventConsumer {

    private Logger log = LoggerFactory.getLogger(PetServiceMarketplaceEventConsumer.class);

    @KafkaHandler
    public void listenServiceClaimCreated(ServiceClaimCreated event) {
        log.debug("Received ServiceClaimCreated in topic: service-marketplace-topic, group: pet-owners, event: {}", event);
    }

    @KafkaHandler
    public void listenServiceClaimUpdated(ServiceClaimUpdated event) {
        log.debug("Received ServiceClaimUpdated in topic: service-marketplace-topic, group: pet-owners, event: {}", event);
    }

    @KafkaHandler
    public void listenServiceClaimDeleted(ServiceClaimDeleted event) {
        log.debug("Received ServiceClaimDeleted in topic: service-marketplace-topic, group: pet-owners, event: {}", event);
    }

    @KafkaHandler(isDefault = true)
    public void handleUnknown(@Payload Object event) {
        log.warn("Unhandled event type: {}", event.getClass());
    }
}
