package com.boop.keycloak.admin;

import com.boop.keycloak.admin.config.KeycloakAdminApiProperties;
import io.micrometer.core.instrument.binder.grpc.ObservationGrpcServerInterceptor;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(value = KeycloakAdminApiProperties.class)
public class BoopKeycloakAdminApplication {

	@Bean
	public ObservationGrpcServerInterceptor interceptor(ObservationRegistry observationRegistry) {
		return new ObservationGrpcServerInterceptor(observationRegistry);
	}

	public static void main(String[] args) {
		SpringApplication.run(BoopKeycloakAdminApplication.class, args);
	}

}
