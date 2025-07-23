package com.jitter.webfluxplayground.project06SSE.dto;

import java.util.UUID;

public record UploadResponse(UUID confirmationId,
                             Long productCount) {
}
