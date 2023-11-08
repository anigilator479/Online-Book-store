package com.example.onlinebookstore.dto.order;

import jakarta.validation.constraints.NotNull;

public record OrderStatusRequestDto(
        @NotNull
        String status
) {
}
