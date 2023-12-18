package com.example.onlinebookstore.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.onlinebookstore.dto.shoppingcart.CartItemDto;
import com.example.onlinebookstore.dto.shoppingcart.CartItemResponseDto;
import com.example.onlinebookstore.dto.shoppingcart.ItemQuantityDto;
import com.example.onlinebookstore.dto.shoppingcart.ShoppingCartResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashSet;
import java.util.Set;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:database/users/add-user.sql")
@Sql(scripts = {
        "classpath:database/books/delete-books.sql",
        "classpath:database/users/delete-all-users.sql",
        "classpath:database/categories/delete-categories.sql",
        "classpath:database/cart/delete-cart-items.sql"
},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
)
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
public class ShoppingCartControllerTest {
    protected static MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @SneakyThrows
    @BeforeAll
    static void beforeAll(
            @Autowired WebApplicationContext applicationContext
    ) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @SneakyThrows
    @WithMockUser(username = "user@email.com", password = "userPassword123")
    @Test
    @DisplayName("Get user's shopping cart")
    void createCart_ValidUser_Success() {
        ShoppingCartResponseDto expected = new ShoppingCartResponseDto(
                1L,
                1L,
                new HashSet<>()
        );
        MvcResult result = mockMvc.perform(get("/api/cart"))
                .andExpect(status().isOk())
                .andReturn();

        ShoppingCartResponseDto actual =
                objectMapper.readValue(result.getResponse()
                        .getContentAsString(), ShoppingCartResponseDto.class);

        assertNotNull(actual);
        assertNotNull(actual.id());
        Assertions.assertTrue(EqualsBuilder.reflectionEquals(expected, actual, "id"));
    }

    @SneakyThrows
    @WithMockUser(username = "user@email.com", password = "userPassword123")
    @Test
    @DisplayName("add cart item to shopping cart")
    @Sql(scripts = {"classpath:database/categories/add-category.sql",
            "classpath:database/books/add-book.sql"})
    void addCartItem_ValidRequestDto_Success() {
        CartItemDto cartItemDto = new CartItemDto(
                1L,
                1
        );
        CartItemResponseDto cartItemResponseDto = new CartItemResponseDto(
                1L,
                1L,
                "Clean Code",
                1
        );

        String jsonRequest = objectMapper.writeValueAsString(cartItemDto);

        MvcResult result = mockMvc.perform(post("/api/cart")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ShoppingCartResponseDto actual =
                objectMapper.readValue(result.getResponse()
                        .getContentAsString(), ShoppingCartResponseDto.class);

        ShoppingCartResponseDto expected = new ShoppingCartResponseDto(
                1L,
                1L,
                Set.of(cartItemResponseDto)
        );

        assertNotNull(actual);
        assertNotNull(actual.id());
        Assertions.assertTrue(EqualsBuilder.reflectionEquals(expected, actual));
    }

    @SneakyThrows
    @WithMockUser(username = "user@email.com", password = "userPassword123")
    @Test
    @DisplayName("Delete cart item by id")
    @Sql(scripts = {"classpath:database/categories/add-category.sql",
            "classpath:database/books/add-book.sql",
            "classpath:database/cart/add-shopping-cart.sql",
            "classpath:database/cart/add-cart-item.sql"})
    void deleteCartItem_ValidId_Success() {
        Long cartItemId = 1L;

        mockMvc.perform(delete("/api/cart/cart-items/" + cartItemId))
                .andExpect(status().isNoContent());
    }

    @SneakyThrows
    @WithMockUser(username = "user@email.com", password = "userPassword123")
    @Test
    @DisplayName("Update cart item quantity")
    @Sql(scripts = {"classpath:database/categories/add-category.sql",
            "classpath:database/books/add-book.sql",
            "classpath:database/cart/add-shopping-cart.sql",
            "classpath:database/cart/add-cart-item.sql"})
    void updateCartItem_ValidRequestDto_Success() {
        Long cartItemId = 1L;

        ItemQuantityDto quantityDto = new ItemQuantityDto(100);

        CartItemResponseDto expectedItem = new CartItemResponseDto(
                1L,
                1L,
                "Clean Code",
                100
        );

        String jsonRequest = objectMapper.writeValueAsString(quantityDto);

        MvcResult result = mockMvc.perform(put("/api/cart/" + cartItemId)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ShoppingCartResponseDto actual =
                objectMapper.readValue(result.getResponse()
                        .getContentAsString(), ShoppingCartResponseDto.class);

        ShoppingCartResponseDto expected = new ShoppingCartResponseDto(
                1L,
                1L,
                Set.of(expectedItem)
        );

        assertNotNull(actual);
        assertNotNull(actual.id());
        Assertions.assertTrue(EqualsBuilder.reflectionEquals(expected, actual));
    }
}
