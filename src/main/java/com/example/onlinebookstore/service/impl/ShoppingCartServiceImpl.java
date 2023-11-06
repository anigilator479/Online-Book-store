package com.example.onlinebookstore.service.impl;

import com.example.onlinebookstore.dto.shoppingcart.CartItemDto;
import com.example.onlinebookstore.dto.shoppingcart.ShoppingCartResponseDto;
import com.example.onlinebookstore.exceptions.EntityNotFoundException;
import com.example.onlinebookstore.mapper.ShoppingCartMapper;
import com.example.onlinebookstore.model.Book;
import com.example.onlinebookstore.model.CartItem;
import com.example.onlinebookstore.model.ShoppingCart;
import com.example.onlinebookstore.model.User;
import com.example.onlinebookstore.repository.BookRepository;
import com.example.onlinebookstore.repository.CartItemRepository;
import com.example.onlinebookstore.repository.ShoppingCartRepository;
import com.example.onlinebookstore.repository.UserRepository;
import com.example.onlinebookstore.service.ShoppingCartService;
import jakarta.transaction.Transactional;
import java.util.HashSet;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;

    @Transactional
    @Override
    public ShoppingCartResponseDto getShoppingCart() {
        return shoppingCartMapper.toResponseCart(findCart());
    }

    @Transactional
    @Override
    public ShoppingCartResponseDto addCartItem(CartItemDto cartItemDto) {
        Book book = bookRepository.findById(cartItemDto.bookId()).orElseThrow(
                () -> new EntityNotFoundException("Non existent book id"));
        ShoppingCart cart = findCart();
        CartItem cartItem = new CartItem();
        cartItem.setBook(book);
        cartItem.setQuantity(cartItemDto.quantity());
        cartItem.setShoppingCart(cart);
        cartItemRepository.save(cartItem);
        return shoppingCartMapper.toResponseCart(cart);
    }

    @Transactional
    @Override
    public void deleteCartItem(Long id) {
        cartItemRepository.deleteCartItemById(id);
    }

    @Transactional
    @Override
    public ShoppingCartResponseDto updateCartItem(Long quantity, Long id) {
        ShoppingCart shoppingCartResponse = findCart();
        CartItem cartItem = cartItemRepository
                .findByIdAndShoppingCartId(id, shoppingCartResponse.getId())
                .orElseThrow(() -> new EntityNotFoundException("Non existent cart item id"));
        cartItem.setQuantity(quantity);
        return shoppingCartMapper.toResponseCart(cartItem.getShoppingCart());
    }

    private ShoppingCart findCart() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Can't find user"));
        return shoppingCartRepository.findShoppingCartByUser(user).orElseGet(() -> {
            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.setCartItems(new HashSet<>());
            shoppingCart.setUser(user);
            return shoppingCartRepository.save(shoppingCart);
        });
    }
}
