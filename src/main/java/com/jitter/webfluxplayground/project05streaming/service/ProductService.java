package com.jitter.webfluxplayground.project05streaming.service;

import com.jitter.webfluxplayground.project05streaming.dto.ProductDto;
import com.jitter.webfluxplayground.project05streaming.mapper.EntityDtoMapper;
import com.jitter.webfluxplayground.project05streaming.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    @Autowired
    private ProductRepository productRepository;

    public Flux<ProductDto> saveProducts(Flux<ProductDto> flux) {
        return flux.map(EntityDtoMapper::toEntity)
                   .as(this.productRepository::saveAll)
                   .map(EntityDtoMapper::toDto);
    }

    public Mono<Long> getProductsCount() {
        return this.productRepository.count();
    }

    public Flux<ProductDto> getAllProducts() {
        return this.productRepository.findAll()
                                     .map(EntityDtoMapper::toDto);
    }
}
