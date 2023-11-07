package com.example.onlinebookstore.service;

import com.example.onlinebookstore.dto.shoppingcart.CartItemDto;
import com.example.onlinebookstore.dto.shoppingcart.ShoppingCartResponseDto;

public interface ShoppingCartService {
    ShoppingCartResponseDto getShoppingCart();

    ShoppingCartResponseDto addCartItem(CartItemDto cartItemDto);

    void deleteCartItem(Long id);

    ShoppingCartResponseDto updateCartItem(Long quantity, Long id);

    void clearCart();
}
