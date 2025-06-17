package com.jitter.webfluxplayground.project02.repository;

import com.jitter.webfluxplayground.project02.entity.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerRepository extends ReactiveCrudRepository<Customer, Integer> {

    @Modifying
    @Query("delete from customer where id=:id")
    Mono<Boolean> deleteByCustomerId(Integer id);

    Flux<Customer> findBy(Pageable pageable);
}
