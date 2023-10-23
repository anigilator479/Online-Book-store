package com.example.onlinebookstore.dto;

public record UserDto(
        Long id,
        String email,
        String firstName,
        String lastName,
        String shippingAddress) {
}
