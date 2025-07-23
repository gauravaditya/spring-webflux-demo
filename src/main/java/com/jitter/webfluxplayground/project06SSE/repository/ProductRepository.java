package com.jitter.webfluxplayground.project06SSE.repository;

import com.jitter.webfluxplayground.project06SSE.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, Integer> {
    Flux<Product> findBy(Pageable pageable);

    Flux<Product> findByPriceBetween(int from, int to);
}
