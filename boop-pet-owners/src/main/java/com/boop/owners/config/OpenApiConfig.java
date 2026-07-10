package com.boop.owners.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

//@SecurityScheme(
//        name = "keycloak", type = SecuritySchemeType.OAUTH2,
//    in = SecuritySchemeIn.HEADER, description = "Keycloak OAuth2 Authorization Code flow",
//flows = @OAuthFlows(
//        password = @OAuthFlow(
//                //authorizationUrl = "\${springdoc.oAuthFlow.authorizationUrl}",
//                tokenUrl = "${springdoc.oAuthFlow.tokenUrl}"
////            ,scopes = [
////                OAuthScope(name = "user:read", description = "read scope"),
////                OAuthScope(name = "user:write", description = "write scope")
////            ]
//)
//    )
//            )


@Configuration
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenApiConfig {
}
