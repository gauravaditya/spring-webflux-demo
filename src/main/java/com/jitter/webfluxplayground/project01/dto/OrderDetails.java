package com.jitter.webfluxplayground.project01.dto;

import java.time.Instant;
import java.util.UUID;

public record OrderDetails(
        UUID id,
        String customerName,
        String productName,
        Integer amount,
        Instant orderDate
) {
}
