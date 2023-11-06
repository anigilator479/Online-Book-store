package com.example.onlinebookstore.mapper;

import com.example.onlinebookstore.dto.shoppingcart.ShoppingCartResponseDto;
import com.example.onlinebookstore.model.ShoppingCart;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        implementationPackage = "<PACKAGE_NAME>.impl",
        uses = CartItemMapper.class)
public interface ShoppingCartMapper {
    @Mapping(source = "userId", target = "user.id")
    ShoppingCart toCart(ShoppingCartResponseDto shoppingCartResponseDto);

    @Mapping(source = "user.id", target = "userId")
    ShoppingCartResponseDto toResponseCart(ShoppingCart shoppingCart);

}
