package com.jitter.webfluxplayground.project02.controller;

import com.jitter.webfluxplayground.project02.dto.CustomerDto;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Objects;

@AutoConfigureWebTestClient
@SpringBootTest(properties = "project=project02")
class CustomerControllerTest {

    private static final Logger log = LoggerFactory.getLogger(CustomerControllerTest.class);

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void allCustomers() {
        this.webTestClient.get()
                          .uri("/customers")
                          .exchange()
                          .expectStatus().is2xxSuccessful()
                          .expectHeader().contentType(MediaType.APPLICATION_JSON)
                          .expectBodyList(CustomerDto.class)
                          .value(list -> log.info("{}", list))
                          .hasSize(10);
    }

    @Test
    public void customerById() {
        this.webTestClient.get()
                          .uri("/customers/1")
                          .exchange()
                          .expectStatus().is2xxSuccessful()
                          .expectBody()
                          .consumeWith(r -> new String(Objects.requireNonNull(r.getResponseBody())))
                          .jsonPath("$.id").isEqualTo(1)
                          .jsonPath("$.name").isEqualTo("sam")
                          .jsonPath("$.email").isEqualTo("sam@gmail.com");
    }

    @Test
    public void createAndDelete() {
        var dto = new CustomerDto(null, "someone", "someone@gmail.com");
        this.webTestClient.post()
                          .uri("/customers")
                          .bodyValue(dto)
                          .exchange()
                          .expectStatus().is2xxSuccessful()
                          .expectBody()
                          .consumeWith(r -> new String(Objects.requireNonNull(r.getResponseBody())))
                          .jsonPath("$.id").isEqualTo(11)
                          .jsonPath("$.name").isEqualTo("someone")
                          .jsonPath("$.email").isEqualTo("someone@gmail.com");

        this.webTestClient.delete()
                          .uri("/customers/11")
                          .exchange()
                          .expectStatus().is2xxSuccessful()
                          .expectBody().isEmpty();

    }

    @Test
    public void updateCustomer() {
        var dto = new CustomerDto(null, "neal", "neal@gmail.com");
        this.webTestClient.put()
                          .uri("/customers/1")
                          .bodyValue(dto)
                          .exchange()
                          .expectStatus().is2xxSuccessful()
                          .expectBody()
                          .consumeWith(r -> new String(Objects.requireNonNull(r.getResponseBody())))
                          .jsonPath("$.id").isEqualTo(1)
                          .jsonPath("$.name").isEqualTo("neal")
                          .jsonPath("$.email").isEqualTo("neal@gmail.com");

    }

    @Test
    public void customerNotFound() {
        this.webTestClient.get()
                          .uri("/customers/11")
                          .exchange()
                          .expectStatus().is4xxClientError()
                          .expectBody().isEmpty();

        this.webTestClient.delete()
                          .uri("/customers/11")
                          .exchange()
                          .expectStatus().is4xxClientError()
                          .expectBody().isEmpty();


        var dto = new CustomerDto(null, "neal", "neal@gmail.com");
        this.webTestClient.put()
                          .uri("/customers/11")
                          .bodyValue(dto)
                          .exchange()
                          .expectStatus().is4xxClientError()
                          .expectBody().isEmpty();
    }
}