package com.example.onlinebookstore.validation;

import com.example.onlinebookstore.dto.user.UserRegistrationRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        UserRegistrationRequestDto user = (UserRegistrationRequestDto) value;
        String firstFieldValue = user.password();
        String secondFieldValue = user.repeatPassword();
        return firstFieldValue != null && firstFieldValue.equals(secondFieldValue);
    }
}
