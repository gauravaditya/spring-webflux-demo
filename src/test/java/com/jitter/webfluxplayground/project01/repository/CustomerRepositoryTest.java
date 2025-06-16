package com.jitter.webfluxplayground.project01.repository;

import com.jitter.webfluxplayground.project01.AbstractTest;
import com.jitter.webfluxplayground.project01.entity.Customer;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CustomerRepositoryTest extends AbstractTest {

    private static final Logger log = LoggerFactory.getLogger(CustomerRepositoryTest.class);

    @Autowired
    private CustomerRepository repository;

    @Test
    public void findAll() {
        this.repository.findAll()
                       .doOnNext(c -> log.info("{}", c))
                       .as(StepVerifier::create)
                       .expectNextCount(10)
                       .expectComplete()
                       .verify();
    }

    @Test
    public void findById() {
        this.repository.findById(3)
                       .doOnNext(c -> log.info("{}", c))
                       .as(StepVerifier::create)
                       .assertNext(c -> assertEquals("jake", c.getName()))
                       .expectComplete()
                       .verify();
    }

    @Test
    public void findByName() {
        this.repository.findByName("mike")
                       .doOnNext(c -> log.info("{}", c))
                       .as(StepVerifier::create)
                       .assertNext(c -> assertEquals("mike", c.getName()))
                       .expectComplete()
                       .verify();
    }

    @Test
    public void findByNameEndingWith() {
        this.repository.findByEmailEndingWith("ke@gmail.com")
                       .doOnNext(c -> log.info("{}", c))
                       .as(StepVerifier::create)
                       .expectNextCount(1)
                       .thenConsumeWhile(c -> true)
                       .verifyComplete();
    }

    @Test
    public void insertAndDeleteCustomer() {
        var customer = new Customer();
        customer.setEmail("johndoe@gmail.com");
        customer.setName("johndoe");

        this.repository.save(customer)
                       .doOnNext(c -> log.info("{}", c))
                       .as(StepVerifier::create)
                       .assertNext(c -> assertNotNull(c.getId()))
                       .expectComplete()
                       .verify();

        this.repository.count()
                       .as(StepVerifier::create)
                       .expectNext(11L)
                       .expectComplete()
                       .verify();

        this.repository.delete(customer)
                       .then(this.repository.count())
                       .as(StepVerifier::create)
                       .expectNext(10L)
                       .expectComplete()
                       .verify();

    }

    @Test
    public void updateCustomer() {
        this.repository.findByName("mike")
                       .doOnNext(c -> c.setName("ethan"))
                       .flatMap(c -> this.repository.save(c))
                       .doOnNext(c -> log.info("{}", c))
                       .as(StepVerifier::create)
                       .assertNext(c -> assertEquals("ethan", c.getName()))
                       .expectComplete()
                       .verify();

    }
}