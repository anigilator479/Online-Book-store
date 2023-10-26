package com.example.onlinebookstore.errors;

import java.time.LocalDateTime;

public record ErrorDto(
        LocalDateTime timestamp,
        Object errorPayload) {
}
