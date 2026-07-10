package com.boop.owners;

import io.micrometer.core.instrument.binder.grpc.ObservationGrpcClientInterceptor;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import reactor.core.publisher.Hooks;

@SpringBootApplication
@ComponentScan("com.boop.*")
public class BoopPetOwnerApplication {

    @Bean
    public ObservationGrpcClientInterceptor interceptor(ObservationRegistry observationRegistry) {
        return new ObservationGrpcClientInterceptor(observationRegistry);
    }

    public static void main(String[] args)
    {
        Hooks.enableAutomaticContextPropagation();
        SpringApplication.run(BoopPetOwnerApplication.class, args);
    }
}
