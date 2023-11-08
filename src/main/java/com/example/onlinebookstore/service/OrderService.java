package com.example.onlinebookstore.service;

import com.example.onlinebookstore.dto.order.OrderItemResponseDto;
import com.example.onlinebookstore.dto.order.OrderRequestDto;
import com.example.onlinebookstore.dto.order.OrderResponseDto;
import java.util.List;

import com.example.onlinebookstore.dto.order.OrderStatusRequestDto;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderResponseDto makeOrder(OrderRequestDto orderRequestDto, Long id);

    List<OrderResponseDto> getOrderHistory(Long id, Pageable pageable);

    List<OrderItemResponseDto> getOrderItems(Long orderId, Pageable pageable);

    OrderItemResponseDto getOrderItem(Long orderId, Long id);

    OrderResponseDto updateOrderStatus(Long id, OrderStatusRequestDto requestDto);
}
