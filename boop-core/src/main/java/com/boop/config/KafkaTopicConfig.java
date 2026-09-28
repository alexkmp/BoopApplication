package com.boop.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaTopicConfig {

    private final BoopKafkaProperties boopKafkaProperties;
    private Logger log = LoggerFactory.getLogger(KafkaTopicConfig.class);

    @Bean
    public KafkaAdmin admin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, boopKafkaProperties.getBootstrapServers());
        return new KafkaAdmin(configs);
    }

    @Bean
    public KafkaAdmin.NewTopics boopTopics() {
        log.info("kafka topic config: {}, {}", boopKafkaProperties.getBootstrapServers(), boopKafkaProperties.getTopicConfigs());
        return new KafkaAdmin.NewTopics(
                boopKafkaProperties.getTopicConfigs().values().stream()
                        .map(this::toNewTopic).toArray(NewTopic[] ::new)
        );
    }

    private NewTopic toNewTopic(BoopTopicConfig topicConfig) {
        return TopicBuilder.name(topicConfig.topicName())
                .partitions(topicConfig.partitionCount())
                .replicas(topicConfig.replicaCount())
                .compact()
                .build();
    }
}
