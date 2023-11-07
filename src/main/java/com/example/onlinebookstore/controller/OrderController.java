package com.example.onlinebookstore.controller;

import com.example.onlinebookstore.dto.order.OrderItemResponseDto;
import com.example.onlinebookstore.dto.order.OrderRequestDto;
import com.example.onlinebookstore.dto.order.OrderResponseDto;
import com.example.onlinebookstore.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Orders management", description = "Endpoints for managing orders")
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Validated
public class OrderController {
    private final OrderService orderService;

    @Operation(summary = "Create order from user's shopping cart content",
            description = "Creates an order using cart items in the shopping cart")
    @PostMapping
    public OrderResponseDto makeOrder(@RequestBody OrderRequestDto orderRequestDto) {
        return orderService.makeOrder(orderRequestDto);
    }

    @Operation(summary = "Get user's orders history",
            description = "Responses user's orders history")
    @GetMapping
    public List<OrderResponseDto> getOrderHistory() {
        return orderService.getOrderHistory();
    }

    @Operation(summary = "Get user's orders items list",
            description = "Responses user's list of order items")
    @GetMapping("/{orderId}/items")
    public List<OrderItemResponseDto> getOrderItem(@PathVariable Long orderId) {
        return orderService.getOrderItems(orderId);
    }

    @Operation(summary = "Get user's orders specific item by id",
            description = "Responses user's specific item from orders items by id")
    @GetMapping("/{orderId}/items/{id}")
    public OrderItemResponseDto getOrderItems(@PathVariable Long orderId, @PathVariable Long id) {
        return orderService.getOrderItem(orderId, id);
    }

    @Operation(summary = "Update order status",
            description = "Updates order's status by id")
    @PutMapping("/{id}")
    public OrderResponseDto updateOrderStatus(@PathVariable @Positive Long id) {
        return orderService.updateOrderStatus(id);
    }
}
