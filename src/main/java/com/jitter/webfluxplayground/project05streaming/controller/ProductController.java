package com.jitter.webfluxplayground.project05streaming.controller;

import com.jitter.webfluxplayground.project05streaming.dto.ProductDto;
import com.jitter.webfluxplayground.project05streaming.dto.UploadResponse;
import com.jitter.webfluxplayground.project05streaming.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("products")
public class ProductController {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService service;

    @PostMapping(value = "upload", consumes = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<UploadResponse> uploadProducts(@RequestBody Flux<ProductDto> flux) {
        log.info("invoked...");
        return this.service.saveProducts(flux.doOnNext(dto -> log.info("request: {}", dto)))
                           .then(this.service.getProductsCount())
                           .map(count -> new UploadResponse(UUID.randomUUID(), count));
    }

    @GetMapping(value = "download-all", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<ProductDto> downloadProducts() {
        return this.service.getAllProducts();
    }
}
