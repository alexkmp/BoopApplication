package com.boop.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JwtConverter implements Converter<Jwt, Mono<? extends AbstractAuthenticationToken>> {

    private JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

    private Logger log = LoggerFactory.getLogger(JwtConverter.class);

    @Value("${jwt.auth.converter.principle-attribute}")
    private String principleAttribute;

    @Value("${jwt.auth.converter.resource-id}")
    private String resourceId;

    @Override
    public Mono<? extends AbstractAuthenticationToken> convert(Jwt jwt) {
        Set<GrantedAuthority> authorities = Stream.concat(
                jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
        extractResourceRoles(jwt).stream()
        ).collect(Collectors.toSet());

        return Mono.just(
                new JwtAuthenticationToken(
                        jwt,
                        authorities,
                        getPrincipleClaimName(jwt)
                )
        );
    }

    private String getPrincipleClaimName(Jwt jwt) {
        String claimName = (principleAttribute != null) ? principleAttribute : JwtClaimNames.SUB;
        return jwt.getClaim(claimName);
    }

    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {
        Map<String, Object> resource;
        if (jwt.getClaims().get("resource_access") == null) {
            log.debug("resource_access field is empty, return empty set");
            return Set.of();
        }
        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
        log.debug("resourceAccess is: {}", resourceAccess);

        if (resourceAccess.get(resourceId) == null) {
            log.debug("resource_access[{}] is null, return empty set", resourceId);
            return Set.of();
        }
        resource = (Map<String, Object>) resourceAccess.get(resourceId);

        List<String> resourceRoles = (List<String>) resource.get("roles");

        log.debug("resourceRoles: {}", resourceRoles);

        return resourceRoles
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
            .collect(Collectors.toSet());
    }
}
