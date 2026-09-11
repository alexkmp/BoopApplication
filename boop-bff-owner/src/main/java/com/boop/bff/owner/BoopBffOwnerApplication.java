package com.boop.bff.owner;

import io.micrometer.core.instrument.binder.grpc.ObservationGrpcClientInterceptor;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Hooks;

@SpringBootApplication
@ComponentScan("com.boop.*")
public class BoopBffOwnerApplication {

    @Bean
    public ObservationGrpcClientInterceptor interceptor(ObservationRegistry observationRegistry) {
        return new ObservationGrpcClientInterceptor(observationRegistry);
    }

    @Bean
    public WebClient webClient(ObservationRegistry observationRegistry) {
        return WebClient.builder().baseUrl("http://boop-pet-owners")
                .observationRegistry(observationRegistry)
                .build();
    }

    public static void main(String[] args)
    {
        Hooks.enableAutomaticContextPropagation();
        SpringApplication.run(BoopBffOwnerApplication.class, args);
    }
}
