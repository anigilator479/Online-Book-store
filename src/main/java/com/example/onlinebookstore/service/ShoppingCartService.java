package com.example.onlinebookstore.service;

import com.example.onlinebookstore.dto.shoppingcart.CartItemDto;
import com.example.onlinebookstore.dto.shoppingcart.ShoppingCartResponseDto;

public interface ShoppingCartService {
    ShoppingCartResponseDto getShoppingCart(String userEmail);

    ShoppingCartResponseDto addCartItem(CartItemDto cartItemDto, String userEmail);

    void deleteCartItem(Long id);

    ShoppingCartResponseDto updateCartItem(int quantity, Long id, String userEmail);

}
