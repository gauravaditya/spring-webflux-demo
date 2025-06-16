package com.jitter.webfluxplayground.project01.repository;

import com.jitter.webfluxplayground.project01.AbstractTest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductRepositoryTest extends AbstractTest {

    private static final Logger log = LoggerFactory.getLogger(ProductRepositoryTest.class);

    @Autowired
    private ProductRepository repository;

    @Test
    public void findByPriceBetween() {
        this.repository.findByPriceBetween(100, 200)
                       .doOnNext(p -> log.info("{}", p))
                       .as(StepVerifier::create)
                       .expectNextCount(1L)
                       .expectComplete()
                       .verify();
    }

    @Test
    public void pageable() {
        this.repository.findBy(PageRequest.of(0, 3, Sort.by("price").ascending()))
                       .doOnNext(p -> log.info("{}", p))
                       .as(StepVerifier::create)
                       .assertNext(p -> assertEquals(200, p.getPrice()))
                       .assertNext(p -> assertEquals(250, p.getPrice()))
                       .assertNext(p -> assertEquals(300, p.getPrice()))
                       .expectComplete()
                       .verify();
    }
}