package com.boop.specialists.event;

import com.boop.event.PetServiceMarketplaceEventConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PetServiceMarketplaceEventConsumerImpl implements PetServiceMarketplaceEventConsumer {

    private Logger log = LoggerFactory.getLogger(PetServiceMarketplaceEventConsumerImpl.class);

    @Override
    public void listenGroupTest(String message) {
        log.info("Received message in topic: service-marketplace-topic, group: test, message: {}", message);
    }

    @Override
    public void listenGroupCreated(String message) {
        log.info("Received message in topic: service-marketplace-topic, group: created, message: {}", message);
    }

    @Override
    public void listenGroupUpdated(String message) {
        log.info("Received message in topic: service-marketplace-topic, group: updated, message: {}", message);
    }

    @Override
    public void listenGroupDeleted(String message) {
        log.info("Received message in topic: service-marketplace-topic, group: deleted, message: {}", message);
    }
}
