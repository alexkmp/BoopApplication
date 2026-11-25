package com.boop.specialists.event;

import com.boop.event.marketplace.ServiceClaimCreated;
import com.boop.event.marketplace.ServiceClaimDeleted;
import com.boop.event.marketplace.ServiceClaimUpdated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "service-marketplace-topic-dlt", groupId = "pet-specialists-dlt")
public class PetServiceMarketplaceDltEventConsumer {

    private Logger log = LoggerFactory.getLogger(PetServiceMarketplaceDltEventConsumer.class);

    @KafkaHandler
    public void listenServiceClaimCreated(ServiceClaimCreated event) {
        log.debug("Received from DLT ServiceClaimCreated in topic: service-marketplace-topic-dlt, " +
                "group: pet-specialists-dlt, event: {}", event);
    }

    @KafkaHandler
    public void listenServiceClaimUpdated(ServiceClaimUpdated event) {
        log.debug("Received from DLT ServiceClaimUpdated in topic: service-marketplace-topic-dlt, " +
                "group: pet-specialists-dlt, event: {}", event);
    }

    @KafkaHandler
    public void listenServiceClaimDeleted(ServiceClaimDeleted event) {
        log.debug("Received from DLT ServiceClaimDeleted in topic: service-marketplace-topic-dlt, " +
                "group: pet-specialists-dlt, event: {}", event);
    }
}
