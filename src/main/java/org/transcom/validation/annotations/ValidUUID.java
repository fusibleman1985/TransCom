package org.transcom.validation.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.transcom.validation.validators.UUIDValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UUIDValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUUID {

    String message() default "{error.invalid_uuid_format}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
