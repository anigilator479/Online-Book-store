package com.example.onlinebookstore.service.impl;

import com.example.onlinebookstore.dto.order.OrderItemResponseDto;
import com.example.onlinebookstore.dto.order.OrderRequestDto;
import com.example.onlinebookstore.dto.order.OrderResponseDto;
import com.example.onlinebookstore.dto.shoppingcart.CartItemResponseDto;
import com.example.onlinebookstore.dto.shoppingcart.ShoppingCartResponseDto;
import com.example.onlinebookstore.exceptions.EntityNotFoundException;
import com.example.onlinebookstore.mapper.OrderItemMapper;
import com.example.onlinebookstore.mapper.OrderMapper;
import com.example.onlinebookstore.model.Order;
import com.example.onlinebookstore.model.OrderItem;
import com.example.onlinebookstore.model.User;
import com.example.onlinebookstore.repository.BookRepository;
import com.example.onlinebookstore.repository.OrderItemRepository;
import com.example.onlinebookstore.repository.OrderRepository;
import com.example.onlinebookstore.repository.UserRepository;
import com.example.onlinebookstore.service.OrderService;
import com.example.onlinebookstore.service.ShoppingCartService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;
    private final ShoppingCartService shoppingCartService;
    private final BookRepository bookRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    @Transactional
    @Override
    public OrderResponseDto makeOrder(OrderRequestDto orderRequestDto) {
        ShoppingCartResponseDto shoppingCart = shoppingCartService.getShoppingCart();
        Order order = new Order();
        User user = getUser();

        Set<OrderItem> orderItems = createOrderItems(shoppingCart, order);
        BigDecimal total = calculateTotal(orderItems);

        order.setUser(user);
        order.setOrderItems(orderItems);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(Order.Status.PENDING);
        order.setTotal(total);
        order.setShippingAddress(orderRequestDto.shippingAddress());

        orderRepository.save(order);
        orderItemRepository.saveAll(orderItems);
        shoppingCartService.clearCart();
        return orderMapper.toResponseOrder(order);
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderResponseDto> getOrderHistory() {
        User user = getUser();
        return orderRepository.findAllByUserId(user.getId()).stream()
                .map(orderMapper::toResponseOrder)
                .toList();
    }

    @Override
    public List<OrderItemResponseDto> getOrderItems(Long orderId) {
        Set<OrderItem> orderItems = orderItemRepository.findAllByOrderId(orderId);
        return orderItems.stream()
                .map(orderItemMapper::toResponseOrderItem)
                .toList();
    }

    @Override
    public OrderItemResponseDto getOrderItem(Long orderId, Long id) {
        return orderItemMapper.toResponseOrderItem(orderItemRepository
                .findByOrderIdAndId(orderId, id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Can't find order item by orderId: %d and itemId: %d",
                                orderId, id))));
    }

    @Override
    public OrderResponseDto updateOrderStatus(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find order by this id: " + id));
        order.setStatus(Order.Status.COMPLETED);
        return orderMapper.toResponseOrder(orderRepository.save(order));
    }

    private Set<OrderItem> createOrderItems(ShoppingCartResponseDto shoppingCart, Order order) {
        return shoppingCart.cartItems().stream()
                .map(cartItem -> mapToOrderItem(order, cartItem))
                .collect(Collectors.toSet());
    }

    private OrderItem mapToOrderItem(Order order, CartItemResponseDto cartItem) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setQuantity(cartItem.quantity());
        orderItem.setBook(bookRepository.getReferenceById(cartItem.bookId()));
        orderItem.setPrice(orderItem.getBook().getPrice()
                .multiply(BigDecimal.valueOf(orderItem.getQuantity())));
        return orderItem;
    }

    private static BigDecimal calculateTotal(Set<OrderItem> orderItems) {
        return orderItems.stream()
                .map(OrderItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private User getUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findUserByEmail(email).orElseThrow(
                () -> new EntityNotFoundException("Can't find user with this email: " + email));
    }
}
