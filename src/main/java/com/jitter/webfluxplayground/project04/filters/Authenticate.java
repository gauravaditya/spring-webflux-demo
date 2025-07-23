package com.jitter.webfluxplayground.project04.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
public class Authenticate implements WebFilter {

    private static final Logger log = LoggerFactory.getLogger(Authenticate.class);

    private static Map<String, Category> SECRET_CATEGORY_MAP = Map.of(
            "secret:standard", Category.STANDARD,
            "secret:prime", Category.PRIME
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        var secret = Optional.ofNullable(exchange.getRequest().getHeaders().get("auth"))
                             .orElse(Collections.emptyList());

        if (!secret.isEmpty()) {
            exchange.getAttributes().put("secret", secret);
            return chain.filter(exchange);
        }

        return Mono.fromRunnable(() -> exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED));
    }
}
