package com.example.onlinebookstore.dto.category;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record CategoryRequestDto(
        @Length(min = 4, max = 20)
        @NotBlank String name,
        @Length(min = 4, max = 255)
        String description
) {
}
