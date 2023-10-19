package com.example.onlinebookstore.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public record CreateBookRequestDto(
        @NotNull
        String title,
        @NotNull
        String author,
        @NotNull
        String isbn,
        @PositiveOrZero
        @NotNull
        BigDecimal price,
        @NotNull
        String description,
        @NotNull
        String coverImage,
        boolean isDeleted) {
}
