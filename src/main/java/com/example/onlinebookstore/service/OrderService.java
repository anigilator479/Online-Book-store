package com.example.onlinebookstore.service;

import com.example.onlinebookstore.dto.order.OrderItemResponseDto;
import com.example.onlinebookstore.dto.order.OrderRequestDto;
import com.example.onlinebookstore.dto.order.OrderResponseDto;
import java.util.List;

public interface OrderService {
    OrderResponseDto makeOrder(OrderRequestDto orderRequestDto);
    List<OrderResponseDto> getOrderHistory();
    List<OrderItemResponseDto> getOrderItems(Long orderId);
    OrderItemResponseDto getOrderItem(Long orderId, Long id);
    OrderResponseDto updateOrderStatus (Long id);
}
