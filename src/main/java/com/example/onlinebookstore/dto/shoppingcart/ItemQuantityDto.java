package com.example.onlinebookstore.dto.shoppingcart;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ItemQuantityDto(@Positive @NotNull Long quantity) {
}
