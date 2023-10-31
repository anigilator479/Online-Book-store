package com.example.onlinebookstore.validation;

import com.example.onlinebookstore.dto.user.UserRegistrationRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch,
        UserRegistrationRequestDto> {
    @Override
    public boolean isValid(UserRegistrationRequestDto value, ConstraintValidatorContext context) {
        String firstFieldValue = value.password();
        String secondFieldValue = value.repeatPassword();
        return firstFieldValue != null && firstFieldValue.equals(secondFieldValue);
    }
}
