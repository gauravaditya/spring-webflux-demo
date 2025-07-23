package com.jitter.webfluxplayground.project05streaming.dto;

import java.util.UUID;

public record UploadResponse(UUID confirmationId,
                             Long productCount) {
}
