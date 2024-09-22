package com.huongnguyen.validator;

import jakarta.validation.Constraint;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import jakarta.validation.Payload;

@Target({ FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy= { NotBlankValidator.class })
public @interface NotBlank {

    String message() default "Field is required";


    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}
