package com.jitter.webfluxplayground.project05streaming;

import com.jitter.webfluxplayground.project05streaming.dto.ProductDto;
import com.jitter.webfluxplayground.project05streaming.dto.UploadResponse;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ProductClient {

    public final WebClient client = WebClient.builder()
                                             .baseUrl("http://localhost:8080")
                                             .build();

    public Mono<UploadResponse> uploadProducts(Flux<ProductDto> dto) {
        return this.client.post()
                          .uri("/products/upload")
                          .contentType(MediaType.APPLICATION_NDJSON)
                          .body(dto, ProductDto.class)
                          .retrieve()
                          .bodyToMono(UploadResponse.class);
    }

    public Flux<ProductDto> downloadProducts() {
        return this.client.get()
                          .uri("/products/download-all")
                          .accept(MediaType.APPLICATION_NDJSON)
                          .retrieve()
                          .bodyToFlux(ProductDto.class);
    }
}
