package com.jitter.webfluxplayground.project01.repository;

import com.jitter.webfluxplayground.project01.AbstractTest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerOrderRepositoryTest extends AbstractTest {

    private static final Logger log = LoggerFactory.getLogger(CustomerOrderRepositoryTest.class);

    @Autowired
    private CustomerOrderRepository repository;

    @Test
    public void productsOrderedByCustomer() {
        this.repository.getProductsOrderedByCustomer("mike")
                       .doOnNext(p -> log.info("{}", p))
                       .as(StepVerifier::create)
                       .expectNextCount(2)
                       .expectComplete()
                       .verify();
    }

    @Test
    public void orderDetailsByProduct() {
        this.repository.getOrderDetailsByProduct("iphone 20")
                       .doOnNext(orderDetails -> log.info("{}", orderDetails))
                       .as(StepVerifier::create)
                       .assertNext(o -> assertEquals(975, o.amount()))
                       .assertNext(o -> assertEquals(950, o.amount()))
                       .expectComplete()
                       .verify();
    }
}