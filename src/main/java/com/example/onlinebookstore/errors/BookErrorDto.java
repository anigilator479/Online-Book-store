package com.example.onlinebookstore.errors;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;

public record BookErrorDto(
        HttpStatus status,
        LocalDateTime timestamp,
        Object errorMessage) {
}
