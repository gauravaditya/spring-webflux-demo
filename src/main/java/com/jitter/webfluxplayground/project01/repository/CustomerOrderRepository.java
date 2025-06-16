package com.jitter.webfluxplayground.project01.repository;

import com.jitter.webfluxplayground.project01.entity.CustomerOrder;
import com.jitter.webfluxplayground.project01.entity.Product;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface CustomerOrderRepository extends ReactiveCrudRepository<CustomerOrder, UUID> {
    @Query(
            """
                SELECT p.*
                FROM customer c
                INNER JOIN customer_order co ON c.id = co.customer_id
                INNER JOIN product p ON co.product_id = p.id
                WHERE c.name = :name
            """
    )
    Flux<Product> getProductsOrderedByCustomer(String name);
}
