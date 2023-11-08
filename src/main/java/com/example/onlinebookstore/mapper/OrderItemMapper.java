package com.example.onlinebookstore.mapper;

import com.example.onlinebookstore.dto.order.OrderItemResponseDto;
import com.example.onlinebookstore.dto.shoppingcart.CartItemResponseDto;
import com.example.onlinebookstore.model.OrderItem;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        implementationPackage = "<PACKAGE_NAME>.impl")
public interface OrderItemMapper {
    @Mapping(source = "bookId", target = "book.id")
    OrderItem toOrderItem(CartItemResponseDto cartItem);

    @Mapping(source = "book.id", target = "bookId")
    OrderItemResponseDto toResponseOrderItem(OrderItem orderItem);
}