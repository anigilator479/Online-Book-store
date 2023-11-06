package com.example.onlinebookstore.controller;

import com.example.onlinebookstore.dto.order.OrderItemResponseDto;
import com.example.onlinebookstore.dto.order.OrderResponseDto;
import com.example.onlinebookstore.dto.shoppingcart.CartItemDto;
import com.example.onlinebookstore.dto.shoppingcart.ItemQuantityDto;
import com.example.onlinebookstore.dto.shoppingcart.ShoppingCartResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Orders management", description = "Endpoints for managing orders")
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Validated
public class OrderController {

    @Operation(summary = "Create order from user's shopping cart content",
            description = "Creates an order using cart items in the shopping cart")
    @PostMapping
    public OrderResponseDto makeOrder() {
        return null;
    }

    @Operation(summary = "Get user's orders history",
            description = "Responses user's orders history")
    @GetMapping
    public List<OrderResponseDto> getOrderHistory() {
        return null;
    }

    @Operation(summary = "Get user's orders items list",
            description = "Responses user's list of order items")
    @GetMapping("/{orderId}/items")
    public List<OrderItemResponseDto> getOrderItems(@PathVariable String orderId) {
        return null;
    }

    @Operation(summary = "Get user's orders specific item by id",
            description = "Responses user's specific item from orders items by id")
    @GetMapping("/{orderId}/items/{id}")
    public List<OrderItemResponseDto> getOrderItem(@PathVariable String orderId, @PathVariable String id) {
        return null;
    }

    @Operation(summary = "Update order status",
            description = "Updates order's status by id")
    @PutMapping("/{id}")
    public ShoppingCartResponseDto updateOrderStatus (@PathVariable @Positive Long id) {
        return null;
    }
}