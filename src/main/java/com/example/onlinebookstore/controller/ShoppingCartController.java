package com.example.onlinebookstore.controller;

import com.example.onlinebookstore.dto.shoppingcart.CartItemDto;
import com.example.onlinebookstore.dto.shoppingcart.ItemQuantityDto;
import com.example.onlinebookstore.dto.shoppingcart.ShoppingCartResponseDto;
import com.example.onlinebookstore.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Shopping cart management", description = "Endpoints for managing shopping cart")
@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Validated
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @Operation(summary = "Get user's shopping cart content",
            description = "Get all information about shopping cart content")
    @GetMapping
    public ShoppingCartResponseDto getCart(Authentication authentication) {
        return shoppingCartService.getShoppingCart(authentication.getName());
    }

    @Operation(summary = "Delete a cart item from a shopping cart",
            description = "Deletes a cart item from a shopping cart by Id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/cart-items/{id}")
    public void delete(@PathVariable @Positive Long id) {
        shoppingCartService.deleteCartItem(id);
    }

    @Operation(summary = "Add cart item", description = "Creates a cart item in shopping cart")
    @PostMapping
    public ShoppingCartResponseDto addCartItem(
            @RequestBody @Valid CartItemDto cartItemDto, Authentication authentication) {
        return shoppingCartService.addCartItem(cartItemDto, authentication.getName());
    }

    @Operation(summary = "Update cart item quantity",
            description = "Updates cart item quantity by Id")
    @PutMapping("/{id}")
    public ShoppingCartResponseDto updateCartItem(@RequestBody @Valid ItemQuantityDto quantityDto,
                                          @PathVariable @Positive Long id,
                                                  Authentication authentication) {
        return shoppingCartService.updateCartItem(quantityDto.quantity(), id,
                authentication.getName());
    }
}
