package com.example.onlinebookstore.service;

import com.example.onlinebookstore.dto.shoppingcart.CartItemDto;
import com.example.onlinebookstore.dto.shoppingcart.CartItemResponseDto;
import com.example.onlinebookstore.dto.shoppingcart.ShoppingCartResponseDto;
import com.example.onlinebookstore.exceptions.EntityNotFoundException;
import com.example.onlinebookstore.mapper.ShoppingCartMapper;
import com.example.onlinebookstore.mapper.impl.CartItemMapperImpl;
import com.example.onlinebookstore.mapper.impl.ShoppingCartMapperImpl;
import com.example.onlinebookstore.model.Book;
import com.example.onlinebookstore.model.CartItem;
import com.example.onlinebookstore.model.ShoppingCart;
import com.example.onlinebookstore.model.User;
import com.example.onlinebookstore.repository.BookRepository;
import com.example.onlinebookstore.repository.CartItemRepository;
import com.example.onlinebookstore.repository.ShoppingCartRepository;
import com.example.onlinebookstore.repository.UserRepository;
import com.example.onlinebookstore.service.impl.ShoppingCartServiceImpl;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@ExtendWith(MockitoExtension.class)
public class ShoppingCartServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @InjectMocks
    private ShoppingCartServiceImpl shoppingCartService;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private BookRepository bookRepository;

    @Spy
    private ShoppingCartMapper shoppingCartMapper =
            new ShoppingCartMapperImpl(new CartItemMapperImpl());

    @Test
    @DisplayName("Verify the correct shopping cart returns")
    public void getShoppingCart_ValidEmail_ShouldReturnValidShoppingCart() {
        User user = new User();
        user.setId(1L);
        user.setEmail("volodya123@gmail.com");

        ShoppingCart cart = new ShoppingCart();
        cart.setId(1L);
        cart.setUser(user);
        cart.setCartItems(new HashSet<>());

        Mockito.when(shoppingCartRepository.findShoppingCartByUserEmail(user.getEmail()))
                .thenReturn(Optional.of(cart));

        ShoppingCartResponseDto actual = shoppingCartService.getShoppingCart(user.getEmail());

        ShoppingCartResponseDto expected = new ShoppingCartResponseDto(
                1L,
                1L,
                new HashSet<>()
        );

        Assertions.assertTrue(EqualsBuilder.reflectionEquals(expected, actual));
    }

    @Test
    @DisplayName("Verify the correct exception message returns with not valid user")
    public void getShoppingCart_NotValidEmail_ShouldThrowException() {
        User user = new User();
        user.setId(1L);
        user.setEmail("volodya12345@gmail.com");

        Mockito.when(userRepository.findUserByEmail(user.getEmail()))
                .thenThrow(new EntityNotFoundException(
                "Can't find user by this email: " + user.getEmail()));

        Exception exception = Assertions.assertThrows(EntityNotFoundException.class,
                () -> shoppingCartService.getShoppingCart(user.getEmail()));

        String expected = "Can't find user by this email: " + user.getEmail();

        String actual = exception.getMessage();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Verify the correct shopping cart returns after add cart item method")
    public void addCartItem_ValidEmailAndDto_ShouldReturnShoppingCartResponseDto() {
        User user = new User();
        user.setId(1L);
        user.setEmail("volodya123@gmail.com");

        ShoppingCart cart = new ShoppingCart();
        cart.setId(1L);
        cart.setUser(user);
        cart.setCartItems(new HashSet<>());

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Good Book");
        book.setIsbn("3123124523452");
        book.setPrice(BigDecimal.TEN);
        book.setAuthor("Anton");

        CartItem cartItem = new CartItem();
        cartItem.setShoppingCart(cart);
        cartItem.setBook(book);
        cartItem.setQuantity(1);
        cartItem.setId(1L);

        CartItemDto cartItemDto = new CartItemDto(
                1L,
                1
        );

        CartItemResponseDto cartItemResponseDto = new CartItemResponseDto(
                null,
                1L,
                book.getTitle(),
                1
                );

        Mockito.when(bookRepository.findById(book.getId()))
                .thenReturn(Optional.of(book));
        Mockito.when(shoppingCartRepository.findShoppingCartByUserEmail(
                user.getEmail())).thenReturn(Optional.of(cart));
        Mockito.when(cartItemRepository.existsByBookIdAndShoppingCartId(
                book.getId(), cart.getId())).thenReturn(false);

        ShoppingCartResponseDto actual =
                shoppingCartService.addCartItem(cartItemDto, user.getEmail());
        ShoppingCartResponseDto expected =
                new ShoppingCartResponseDto(
                        1L, user.getId(), Set.of(cartItemResponseDto));

        Assertions.assertTrue(EqualsBuilder.reflectionEquals(expected, actual));
    }

    @Test
    @DisplayName("Verify correct delete method invocation")
    public void deleteCartItemById_ValidId_Success() {
        Long id = 1L;

        Mockito.doNothing().when(cartItemRepository).deleteById(id);

        shoppingCartService.deleteCartItem(id);

        Mockito.verify(cartItemRepository).deleteById(id);
        Mockito.verifyNoMoreInteractions(cartItemRepository);
    }
}
