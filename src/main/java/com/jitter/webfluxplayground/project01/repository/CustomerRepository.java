package com.jitter.webfluxplayground.project01.repository;

import com.jitter.webfluxplayground.project01.entity.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface CustomerRepository extends ReactiveCrudRepository<Customer, Integer> {

    Flux<Customer> findByName(String name);

    Flux<Customer> findByEmailEndingWith(String suffix);
}
