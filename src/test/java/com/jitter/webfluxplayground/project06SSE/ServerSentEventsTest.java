package com.jitter.webfluxplayground.project06SSE;

import com.jitter.webfluxplayground.project06SSE.dto.ProductDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

import java.time.Duration;

@AutoConfigureWebTestClient
@SpringBootTest(properties = "project=project06SSE")
public class ServerSentEventsTest {

    private static final Logger log = LoggerFactory.getLogger(ServerSentEventsTest.class);

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void serverSentEvents() {
        this.webTestClient.get()
                          .uri("/products/stream/80")
                          .accept(MediaType.TEXT_EVENT_STREAM)
                          .exchange()
                          .expectStatus().is2xxSuccessful()
                          .returnResult(ProductDto.class)
                          .getResponseBody()
                          .take(3)
                          .doOnNext(productDto -> log.info("received: {}", productDto))
                          .collectList()
                          .as(StepVerifier::create)
                          .assertNext(data -> {
                              Assertions.assertEquals(3, data.size());
                              Assertions.assertTrue(data.stream().allMatch(item -> item.price() <= 80));
                          })
                          .expectComplete()
                          .verify(Duration.ofSeconds(5));
    }
}
