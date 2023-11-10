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
import java.util.HashSet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public ShoppingCartResponseDto getShoppingCart(Long userId) {
        return shoppingCartMapper.toResponseCart(findCart(userId));
    }

    @Transactional
    @Override
    public ShoppingCartResponseDto addCartItem(CartItemDto cartItemDto, Long userId) {
        Book book = bookRepository.findById(cartItemDto.bookId()).orElseThrow(
                () -> new EntityNotFoundException("Non existent book id: " + cartItemDto.bookId()));
        ShoppingCart cart = findCart(userId);
        CartItem cartItem;
        if (cartItemRepository.existsByBookIdAndShoppingCartId(book.getId(), cart.getId())) {
            cartItem = cartItemRepository.findByBookIdAndShoppingCartId(book.getId(), cart.getId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Can't find books with this id: " + book.getId()));
            cartItem.setQuantity(cartItem.getQuantity() + cartItemDto.quantity());
        } else {
            cartItem = new CartItem();
            cartItem.setBook(book);
            cartItem.setQuantity(cartItemDto.quantity());
            cartItem.setShoppingCart(cart);
            cartItemRepository.save(cartItem);
        }
        return shoppingCartMapper.toResponseCart(cart);
    }

    @Override
    public void deleteCartItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    @Transactional
    @Override
    public ShoppingCartResponseDto updateCartItem(int quantity, Long id, Long userId) {
        ShoppingCart shoppingCartResponse = findCart(userId);
        CartItem cartItem = cartItemRepository
                .findByIdAndShoppingCartId(id, shoppingCartResponse.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Non existent cart item id: " + shoppingCartResponse.getId()));
        cartItem.setQuantity(quantity);
        return shoppingCartMapper.toResponseCart(cartItem.getShoppingCart());
    }

    private ShoppingCart findCart(Long userId) {
        return shoppingCartRepository.findShoppingCartByUserId(userId)
                .orElseGet(() -> creatCartForUser(userRepository.findById(userId).orElseThrow(
                        () -> new EntityNotFoundException("Can't find user by this id: "
                                + userId))));
    }

    private ShoppingCart creatCartForUser(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCartItems(new HashSet<>());
        shoppingCart.setUser(user);
        return shoppingCartRepository.save(shoppingCart);
    }
}
