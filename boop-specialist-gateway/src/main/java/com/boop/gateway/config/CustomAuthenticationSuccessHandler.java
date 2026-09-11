package com.boop.gateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.security.oauth2.client.autoconfigure.OAuth2ClientProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.DefaultServerRedirectStrategy;
import org.springframework.security.web.server.ServerRedirectStrategy;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.savedrequest.ServerRequestCache;
import org.springframework.security.web.server.savedrequest.WebSessionServerRequestCache;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

public class CustomAuthenticationSuccessHandler extends RedirectServerAuthenticationSuccessHandler {

    private Logger log = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);

    private ServerRedirectStrategy redirectStrategy = new DefaultServerRedirectStrategy();
    private ServerRequestCache requestCache = new WebSessionServerRequestCache();

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        ServerWebExchange exchange = webFilterExchange.getExchange();
        return this.requestCache.getRedirectUri(exchange)
                .defaultIfEmpty(URI.create("/access-token"))
                .flatMap((location) -> this.redirectStrategy.sendRedirect(exchange, location));
    }

//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException, IOException {
//        String redirectURL = request.getContextPath();
//        log.debug("initial redirectURL: {}", redirectURL);
//
//        log.debug("authorities: {}", authentication.getAuthorities().stream().map(a->a.getAuthority()).collect(Collectors.toUnmodifiableList()));
//
//        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_OWNER"))) {
//            redirectURL = "/api/pet-owners";
//        } else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_SPECIALIST"))) {
//            redirectURL = "/api/pet-specialists";
//        }
//        log.debug("redirectURL: {}", redirectURL);
//
//        response.sendRedirect(redirectURL);
//        response.sendRedirect("/access-token");
//    }
}
