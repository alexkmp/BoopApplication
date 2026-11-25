package com.boop.event;

import com.boop.event.marketplace.ServiceClaimCreated;
import com.boop.event.marketplace.ServiceClaimDeleted;
import com.boop.event.marketplace.ServiceClaimUpdated;

public enum OutboxEventType {
    SERVICE_CLAIM_CREATED("ServiceClaimCreated", ServiceClaimCreated.class),
    SERVICE_CLAIM_UPDATED("ServiceClaimUpdated", ServiceClaimUpdated.class),
    SERVICE_CLAIM_DELETED("ServiceClaimDeleted",ServiceClaimDeleted .class);

    private final String code;
    private final Class<?> type;

    OutboxEventType(String code, Class<?> type) {
        this.code = code;
        this.type = type;
    }

    public String code() { return code; }
    public Class<?> type() { return type; }
}
