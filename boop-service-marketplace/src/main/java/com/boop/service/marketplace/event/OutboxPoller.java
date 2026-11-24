package com.boop.service.marketplace.event;

import com.boop.event.OutboxStatus;
import com.boop.service.marketplace.persistence.entity.OutboxEvent;
import com.boop.service.marketplace.persistence.repository.OutboxEventRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class OutboxPoller {

    private Logger log = LoggerFactory.getLogger(OutboxPoller.class);

    private static final String TOPIC = "service-marketplace-topic";
    private static final String KEY_PREFIX = "service-claim-";

    private final OutboxEventRepository outboxRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Qualifier("jackson2ObjectMapper")
    private final ObjectMapper jackson2;

    @Scheduled(fixedDelayString = "${outbox.poller.fixed-delay}")
    public void poll() {
        List<OutboxEvent> events = outboxRepository
                .findTop100ByStatusOrderByCreationDateAsc(OutboxStatus.PENDING);
        if (events.isEmpty()) return;

        for (OutboxEvent event : events) {
            try {
                if (event.getType() == null) {
                    log.error("Unknown event type: {}", event.getType());
                    markFailed(event.getId());
                    continue;
                }
                Object payload = jackson2.treeToValue(event.getPayload(), event.getType().type());

                kafkaTemplate.send(TOPIC, KEY_PREFIX + event.getAggregateId(), payload)
                        .get(5, TimeUnit.SECONDS);

                markSent(event.getId());
            } catch (Exception ex) {
                log.error("Failed to publish event {}", event.getId(), ex);
                markFailed(event.getId());
            }
        }
    }

    @Transactional
    public void markSent(Long id) {
        outboxRepository.findById(id).ifPresent(e -> {
            e.markSent();
            outboxRepository.save(e);
        });
    }

    @Transactional
    public void markFailed(Long id) {
        outboxRepository.findById(id).ifPresent(e -> {
            e.markFailed();
            outboxRepository.save(e);
        });
    }
}
