package com.boop.service.marketplace.event;

import com.boop.owners.dto.PetOwnerDataFullResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PetOwnerEventConsumer {

    private Logger log = LoggerFactory.getLogger(PetOwnerEventConsumer.class);

    @KafkaListener(topics = "owner-topic", groupId = "byLogin")
    public void listenGroupByLogin(PetOwnerDataFullResponse response) {
        log.info("Received message in topic: owner-topic, group: byLogin, response: {}", response);
    }

    @KafkaListener(topics = "owner-topic", groupId = "updated")
    public void listenGroupUpdated(PetOwnerDataFullResponse response) {
        log.info("Received message in topic: owner-topic, group: updated, message: {}", response);
    }

    @KafkaListener(topics = "owner-topic", groupId = "deleted")
    public void listenGroupDeleted(PetOwnerDataFullResponse response) {
        log.info("Received message in topic: owner-topic, group: deleted, message: {}", response);
    }
}
