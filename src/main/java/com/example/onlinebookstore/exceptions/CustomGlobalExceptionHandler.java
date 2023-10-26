package com.example.onlinebookstore.exceptions;

import com.example.onlinebookstore.errors.ErrorDto;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler {
    @ExceptionHandler(RegistrationException.class)
    protected ResponseEntity<Object> handleRegistrationException(RegistrationException ex) {
        return createResponseEntity(
                new ErrorDto(LocalDateTime.now(), ex.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
        return createResponseEntity(
                new ErrorDto(LocalDateTime.now(), ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getAllErrors().stream()
                .map(this::getErrorMessage)
                .toList();
        ErrorDto errorDto = new ErrorDto(
                LocalDateTime.now(), errors);
        return createResponseEntity(errorDto);
    }

    private ResponseEntity<Object> createResponseEntity(ErrorDto errorDto) {
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    private String getErrorMessage(ObjectError objectError) {
        if (objectError instanceof FieldError) {
            String field = ((FieldError) objectError).getField();
            String message = objectError.getDefaultMessage();
            return String.join(" ", field, message);
        }
        return objectError.getDefaultMessage();
    }
}
