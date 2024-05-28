package com.example.dlarticle.api.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateArticleRequest(
        @NotBlank
        String title,
        @NotBlank
        String detail
) {
}
