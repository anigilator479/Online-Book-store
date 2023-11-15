package com.example.onlinebookstore.dto.shoppingcart;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CartItemDto(
        @Positive @NotNull Long bookId,
        @Positive int quantity) {
}
