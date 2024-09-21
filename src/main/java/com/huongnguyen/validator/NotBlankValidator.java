package com.huongnguyen.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotBlankValidator implements ConstraintValidator<NotBlank, String> {

    private boolean notBlank;

    @Override
    public void initialize(NotBlank constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        notBlank = constraintAnnotation.notBlank();
    }

    @Override
    public boolean isValid(String string, ConstraintValidatorContext constraintValidatorContext) {

        return !(string.isEmpty() || string.isBlank());
    }
}
