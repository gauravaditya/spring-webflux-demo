package com.jitter.webfluxplayground.project04.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Service
@Order(2)
public class WebFilterDemo2 implements WebFilter {
    private static final Logger log = LoggerFactory.getLogger(WebFilterDemo2.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        log.info("WebfilterDemo2/filter: received");
        return chain.filter(exchange);
    }
}
