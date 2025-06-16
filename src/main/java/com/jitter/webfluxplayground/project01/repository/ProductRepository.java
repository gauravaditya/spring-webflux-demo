package com.jitter.webfluxplayground.project01.repository;

import com.jitter.webfluxplayground.project01.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;


public interface ProductRepository extends ReactiveCrudRepository<Product, Integer> {
    Flux<Product> findByPriceBetween(Integer from, Integer to);

    Flux<Product> findBy(Pageable pageable);
}
