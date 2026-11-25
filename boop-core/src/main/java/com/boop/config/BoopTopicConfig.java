package com.boop.config;

public record BoopTopicConfig(String topicName, Integer partitionCount, Integer replicaCount) {
}
