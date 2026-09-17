package com.boop.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "redis")
@Data
public class BoopRedisProperties {

    private Map<String, Integer> ttl;
}
