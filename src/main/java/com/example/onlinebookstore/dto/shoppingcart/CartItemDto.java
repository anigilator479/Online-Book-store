package com.example.onlinebookstore.dto.shoppingcart;

import jakarta.validation.constraints.Positive;

public record CartItemDto(
        @Positive Long bookId,
        @Positive Long quantity) {
}
