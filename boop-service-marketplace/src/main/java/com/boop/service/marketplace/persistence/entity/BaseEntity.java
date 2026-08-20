package com.boop.service.marketplace.persistence.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(BaseEntity.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    @Getter
    Long id;

    @Version
    @Column(name = "version")
    @Getter @Setter Long version = 1L;

    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    @Getter @Setter
    LocalDateTime creationDate = LocalDateTime.now();

    @Column(name = "modify_date")
    @Temporal(TemporalType.TIMESTAMP)
    @Getter @Setter LocalDateTime modifyDate = LocalDateTime.now();

    @PreUpdate
    public void preUpdate() {
        this.modifyDate = LocalDateTime.now();
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && id != null && obj instanceof BaseEntity && id.equals(((BaseEntity) obj).getId());
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            logger.error("toString", e);
        }
        return "Some JsonProcessingException";
    }
}
