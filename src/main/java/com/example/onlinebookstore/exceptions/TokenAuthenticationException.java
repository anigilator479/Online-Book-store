package com.example.onlinebookstore.exceptions;

public class TokenAuthenticationException extends RuntimeException {
    public TokenAuthenticationException(String msg) {
        super(msg);
    }
}
