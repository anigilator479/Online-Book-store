package com.example.onlinebookstore.dto;

import java.math.BigDecimal;

public record CreateBookRequestDto(
        Long id,
        String title,
        String author,
        String isbn,
        BigDecimal price,
        boolean isDeleted,
        String description,
        String coverImage) {
}
