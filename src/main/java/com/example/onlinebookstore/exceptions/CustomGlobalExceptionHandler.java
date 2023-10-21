package com.example.onlinebookstore.exceptions;

import com.example.onlinebookstore.errors.BookErrorDto;
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
    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
        return createResponseEntity(
                new BookErrorDto(HttpStatus.NOT_FOUND,LocalDateTime.now(), ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getAllErrors().stream()
                .map(this::getErrorMessage)
                .toList();
        BookErrorDto bookErrorDto = new BookErrorDto(HttpStatus.BAD_REQUEST,
                LocalDateTime.now(), errors);
        return createResponseEntity(bookErrorDto);
    }

    private ResponseEntity<Object> createResponseEntity(BookErrorDto bookErrorDto) {
        return new ResponseEntity<>(bookErrorDto, bookErrorDto.status());
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
