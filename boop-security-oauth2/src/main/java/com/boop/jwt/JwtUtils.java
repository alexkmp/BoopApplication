package com.boop.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {

    @Value("${jwt.auth.converter.principle-attribute}")
    private String principleAttribute;

    public String getPrincipalNameFromAuth(Authentication auth) {
        if(auth instanceof JwtAuthenticationToken jwtAuth) {
            String claimName = (principleAttribute != null) ? principleAttribute : JwtClaimNames.SUB;
            final var jwt = jwtAuth.getToken();
            return jwt.getClaim(claimName);
        } else {
            throw new RuntimeException("Cannot get principal name from auth");
        }
    }

    public String getToken(Authentication auth) {
        if(auth instanceof JwtAuthenticationToken jwtAuth) {
            return jwtAuth.getToken().getTokenValue();
        } else {
            throw new RuntimeException("Cannot get token from auth");
        }
    }
}
