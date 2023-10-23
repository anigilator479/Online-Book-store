package com.example.onlinebookstore.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateUserRequestDto(
        @NotBlank
        String email,
        @NotBlank
        String password,
        @NotBlank
        String repeatPassword,
        @NotBlank
        String firstName,
        @NotBlank
        String lastName,
        @NotBlank
        String shippingAddress
){
}
