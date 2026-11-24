package com.boop.service.marketplace.persistence.entity;

import com.boop.event.OutboxAggregateType;
import com.boop.event.OutboxEventType;
import com.boop.event.OutboxStatus;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Table(name = "outbox_event")
@Access(AccessType.FIELD)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OutboxEvent extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OutboxStatus status;

    @Column(name = "aggregate_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private OutboxAggregateType aggregateType;

    @Column(name = "aggregate_id", nullable = false)
    private Long aggregateId;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private OutboxEventType type;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "payload", columnDefinition = "jsonb", nullable = false)
    private JsonNode payload;

    private int attempts;

    @Column(name = "procession_date")
    private LocalDateTime processionDate;

    public void markSent() {
        this.status = OutboxStatus.SENT;
        this.processionDate = LocalDateTime.now();
    }

    public void markFailed() {
        this.attempts++;
        if (this.attempts >= 5) this.status = OutboxStatus.FAILED;
    }
}
