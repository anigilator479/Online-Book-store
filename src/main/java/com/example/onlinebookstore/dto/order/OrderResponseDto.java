package com.example.onlinebookstore.dto.order;

import com.example.onlinebookstore.model.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public record OrderResponseDto(
        Long id,
        Long userId,
        Set<OrderItemResponseDto> cartItems,
        LocalDateTime orderDate,
        BigDecimal total,
        Order.Status status
) {
}
