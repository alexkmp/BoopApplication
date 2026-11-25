package com.boop.service.marketplace.persistence.repository;

import com.boop.event.OutboxStatus;
import com.boop.service.marketplace.persistence.entity.OutboxEvent;import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OutboxEventRepository extends CrudRepository<OutboxEvent, Long> {

    List<OutboxEvent> findAll();

    List<OutboxEvent> findTop100ByStatusOrderByCreationDateAsc(OutboxStatus outboxStatus);


}
