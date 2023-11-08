package com.example.onlinebookstore.service.impl;

import com.example.onlinebookstore.dto.order.OrderItemResponseDto;
import com.example.onlinebookstore.dto.order.OrderRequestDto;
import com.example.onlinebookstore.dto.order.OrderResponseDto;
import com.example.onlinebookstore.dto.order.OrderStatusRequestDto;
import com.example.onlinebookstore.exceptions.EntityNotFoundException;
import com.example.onlinebookstore.mapper.OrderItemMapper;
import com.example.onlinebookstore.mapper.OrderMapper;
import com.example.onlinebookstore.model.CartItem;
import com.example.onlinebookstore.model.Order;
import com.example.onlinebookstore.model.OrderItem;
import com.example.onlinebookstore.model.ShoppingCart;
import com.example.onlinebookstore.model.User;
import com.example.onlinebookstore.repository.CartItemRepository;
import com.example.onlinebookstore.repository.OrderItemRepository;
import com.example.onlinebookstore.repository.OrderRepository;
import com.example.onlinebookstore.repository.ShoppingCartRepository;
import com.example.onlinebookstore.repository.UserRepository;
import com.example.onlinebookstore.service.OrderService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderItemMapper orderItemMapper;
    private final ShoppingCartRepository shoppingCartRepository;

    @Transactional
    @Override
    public OrderResponseDto makeOrder(OrderRequestDto orderRequestDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Can't find user by id: " + userId));
        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Can't "));
        Order order = createOrder(shoppingCart.getCartItems());

        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(Order.Status.PENDING);
        order.setShippingAddress(orderRequestDto.shippingAddress());
        order.setTotal(calculateTotal(order.getOrderItems()));

        orderRepository.save(order);
        cartItemRepository.deleteAllByShoppingCartId(shoppingCart.getId());
        return orderMapper.toResponseOrder(order);
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderResponseDto> getOrderHistory(Long id, Pageable pageable) {
        Page<Order> orders = orderRepository.findAllByUserId(id, pageable);
        return orders.stream()
                .map(orderMapper::toResponseOrder)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderItemResponseDto> getOrderItems(Long orderId, Pageable pageable) {
        Page<OrderItem> orderItems = orderItemRepository.findAllByOrderId(orderId, pageable);
        return orderItems.stream()
                .map(orderItemMapper::toResponseOrderItem)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public OrderItemResponseDto getOrderItem(Long orderId, Long id) {
        return orderItemMapper.toResponseOrderItem(orderItemRepository
                .findByOrderIdAndId(orderId, id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Can't find order item by orderId: %d and itemId: %d",
                                orderId, id))));
    }

    @Transactional
    @Override
    public OrderResponseDto updateOrderStatus(Long id, OrderStatusRequestDto requestDto) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find order by this id: " + id));
        order.setStatus(Order.Status.valueOf(requestDto.status()));
        return orderMapper.toResponseOrder(order);
    }

    private Order createOrder(Set<CartItem> cartItemSet) {
        Order order = new Order();
        OrderItem orderItem = new OrderItem();
        Set<OrderItem> orderItems = new HashSet<>();

        cartItemSet.forEach(cartItem -> {
                    orderItem.setQuantity(cartItem.getQuantity());
                    orderItem.setBook(cartItem.getBook());
                    orderItem.setPrice(cartItem.getBook().getPrice()
                            .multiply(BigDecimal.valueOf(cartItem.getQuantity())));
                    orderItem.setOrder(order);
                    orderItems.add(orderItem);
                }
        );
        order.setOrderItems(orderItems);
        return order;
    }

    private static BigDecimal calculateTotal(Set<OrderItem> orderItems) {
        return orderItems.stream()
                .map(OrderItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
