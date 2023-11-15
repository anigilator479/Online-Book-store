package com.example.onlinebookstore.dto.order;

public record OrderItemResponseDto(
        Long id,
        Long bookId,
        Long quantity
) {
}
