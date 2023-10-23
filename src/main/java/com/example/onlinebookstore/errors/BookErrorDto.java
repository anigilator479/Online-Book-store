package com.example.onlinebookstore.errors;

import java.time.LocalDateTime;

public record BookErrorDto(
        LocalDateTime timestamp,
        Object errorPayload) {
}
