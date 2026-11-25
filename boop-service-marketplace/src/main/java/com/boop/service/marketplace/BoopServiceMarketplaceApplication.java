package com.boop.service.marketplace;

import io.micrometer.core.instrument.binder.grpc.ObservationGrpcClientInterceptor;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;
import reactor.core.publisher.Hooks;

@SpringBootApplication
@ComponentScan("com.boop.*")
@EnableCaching
@EnableScheduling
@EnableKafka
public class BoopServiceMarketplaceApplication {

    @Bean
    public ObservationGrpcClientInterceptor interceptor(ObservationRegistry observationRegistry) {
        return new ObservationGrpcClientInterceptor(observationRegistry);
    }

    public static void main(String[] args)
    {
        Hooks.enableAutomaticContextPropagation();
        SpringApplication.run(BoopServiceMarketplaceApplication.class, args);
    }
}
