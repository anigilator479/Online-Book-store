package com.example.onlinebookstore.mapper;

import com.example.onlinebookstore.dto.shoppingcart.CartItemResponseDto;
import com.example.onlinebookstore.model.CartItem;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        implementationPackage = "<PACKAGE_NAME>.impl")
public interface CartItemMapper {
    @Mapping(source = "book.id", target = "bookId")
    CartItemResponseDto toResponseDto(CartItem cartItem);
}
