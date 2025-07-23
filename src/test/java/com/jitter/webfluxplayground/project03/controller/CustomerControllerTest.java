package com.jitter.webfluxplayground.project03.controller;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Objects;

@AutoConfigureWebTestClient
@SpringBootTest(properties = "project=project03")
class CustomerControllerTest {

    private static final Logger log = LoggerFactory.getLogger(CustomerControllerTest.class);

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void customerById() {
        this.webTestClient.get()
                          .uri("/customers/1")
                          .exchange()
                          .expectStatus().is2xxSuccessful()
                          .expectBody()
                          .consumeWith(r -> new String(Objects.requireNonNull(r.getResponseBody())))
                          .jsonPath("$.id").isEqualTo(1)
                          .jsonPath("$.name").isEqualTo("sam");

        this.webTestClient.get()
                          .uri("/customers/11")
                          .exchange()
                          .expectStatus().is4xxClientError()
                          .expectBody()
                          .consumeWith(r -> new String(Objects.requireNonNull(r.getResponseBody())))
                          .jsonPath("$.title").isNotEmpty()
                          .jsonPath("$.type").isNotEmpty()
                          .jsonPath("$.status").isEqualTo(404);
    }
}