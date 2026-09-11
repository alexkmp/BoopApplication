package com.boop.gateway.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    OpenAPI customOpenAPI() {
        Server userServer = new Server();
        userServer.url("https://localhost:8443");
        return new OpenAPI()
                .servers(List.of(userServer))
                .info(
                        new Info()
                                .title("API Gateway service")
                                .version("1.0")
                                .description("BoopApplication")
                );
    }
}
