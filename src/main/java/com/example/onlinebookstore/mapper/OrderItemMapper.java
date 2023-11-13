package com.example.onlinebookstore.mapper;

import com.example.onlinebookstore.dto.order.OrderItemResponseDto;
import com.example.onlinebookstore.model.CartItem;
import com.example.onlinebookstore.model.OrderItem;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

import java.math.BigDecimal;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        implementationPackage = "<PACKAGE_NAME>.impl")
public interface OrderItemMapper {
    @Mapping(target = "price", expression = "java(calculatePrice(cartItem))")
    OrderItem toOrderItem(CartItem cartItem);
    @Mapping(source = "book.id", target = "bookId")
    OrderItemResponseDto toResponseOrderItem(OrderItem orderItem);
    default BigDecimal calculatePrice(CartItem cartItem) {
        return cartItem.getBook().getPrice()
                .multiply(BigDecimal.valueOf(cartItem.getQuantity()));
    }
}
