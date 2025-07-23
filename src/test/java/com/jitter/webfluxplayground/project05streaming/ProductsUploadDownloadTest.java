package com.jitter.webfluxplayground.project05streaming;

import com.jitter.webfluxplayground.project05streaming.dto.ProductDto;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.nio.file.Path;

public class ProductsUploadDownloadTest {

    private static final Logger log = LoggerFactory.getLogger(ProductsUploadDownloadTest.class);
    private final ProductClient client = new ProductClient();

    @Test
    void upload() {
        var flux = Flux.range(1, 1000000)
                       .map(i -> new ProductDto(null, "product-" + i, i));

        this.client.uploadProducts(flux)
                   .doOnNext(dto -> log.info("response: {}", dto))
                   .then()
                   .as(StepVerifier::create)
                   .expectComplete()
                   .verify();
    }

    @Test
    void downloadAll() {
        this.client.downloadProducts()
                   .map(ProductDto::toString)
                   .as(flux -> FileWriter.create(flux, Path.of("products.txt")))
                   .then()
                   .as(StepVerifier::create)
                   .verifyComplete();
    }
}
