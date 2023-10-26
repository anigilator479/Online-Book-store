package com.example.onlinebookstore.dto.user;

public record UserResponseDto(
        Long id,
        String email,
        String lastName,
        String firstName,
        String shippingAddress) {
}
