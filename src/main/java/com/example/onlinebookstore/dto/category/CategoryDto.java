package com.example.onlinebookstore.dto.category;

import jakarta.validation.constraints.NotBlank;

public record CategoryDto(@NotBlank String name, String description) {
}
