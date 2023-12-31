package com.example.onlinebookstore.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record UserLoginRequestDto(
        @Email
        @NotBlank
        String email,
        @Length(min = 4, max = 50)
        @NotBlank
        String password
) {
}
