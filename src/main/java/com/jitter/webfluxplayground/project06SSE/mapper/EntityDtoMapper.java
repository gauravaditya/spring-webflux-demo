package com.jitter.webfluxplayground.project06SSE.mapper;

import com.jitter.webfluxplayground.project06SSE.dto.ProductDto;
import com.jitter.webfluxplayground.project06SSE.entity.Product;

public class EntityDtoMapper {
    public static Product toEntity(ProductDto dto) {
        Product product = new Product();
        product.setId(dto.id());
        product.setDescription(dto.description());
        product.setPrice(dto.price());

        return product;
    }

    public static ProductDto toDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getDescription(),
                product.getPrice()
        );
    }
}
