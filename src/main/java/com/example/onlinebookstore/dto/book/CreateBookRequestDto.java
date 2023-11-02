package com.example.onlinebookstore.dto.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;
import org.hibernate.validator.constraints.Length;

public record CreateBookRequestDto(
        @Length(min = 4, max = 50)
        @NotBlank
        String title,
        @Length(min = 4, max = 50)
        @NotBlank
        String author,
        @Length(min = 4, max = 50)
        @NotBlank
        String isbn,
        @Positive
        @NotNull
        BigDecimal price,
        @Length(min = 4, max = 255)
        String description,
        @Length(min = 4, max = 255)
        String coverImage,
        @NotEmpty
        List<@Positive Long> categoriesIds
) {
}
