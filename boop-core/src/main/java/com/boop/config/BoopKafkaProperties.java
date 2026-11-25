package com.boop.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "spring.kafka")
@Data
public class BoopKafkaProperties {

    private String bootstrapServers;

    private Map<String, BoopTopicConfig> topicConfigs;
}
