package com.example.onlinebookstore.service;

import com.example.onlinebookstore.dto.shoppingcart.CartItemDto;
import com.example.onlinebookstore.dto.shoppingcart.ShoppingCartResponseDto;

public interface ShoppingCartService {
    ShoppingCartResponseDto getShoppingCart(Long userId);

    ShoppingCartResponseDto addCartItem(CartItemDto cartItemDto, Long userId);

    void deleteCartItem(Long id);

    ShoppingCartResponseDto updateCartItem(int quantity, Long id, Long userId);

}
