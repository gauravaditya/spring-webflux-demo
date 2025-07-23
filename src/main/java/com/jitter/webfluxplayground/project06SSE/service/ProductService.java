package com.jitter.webfluxplayground.project06SSE.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jitter.webfluxplayground.project06SSE.dto.ProductDto;
import com.jitter.webfluxplayground.project06SSE.mapper.EntityDtoMapper;
import com.jitter.webfluxplayground.project06SSE.repository.ProductRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Service
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private Sinks.Many<ProductDto> sink;

    public Mono<ProductDto> saveProduct(Mono<ProductDto> mono) {
        return mono.map(EntityDtoMapper::toEntity)
                   .flatMap(this.productRepository::save)
                   .map(EntityDtoMapper::toDto)
                   .doOnNext(this.sink::tryEmitNext);
    }

   public Flux<ProductDto> getProductStream() {
        return this.sink.asFlux();
    }
}
