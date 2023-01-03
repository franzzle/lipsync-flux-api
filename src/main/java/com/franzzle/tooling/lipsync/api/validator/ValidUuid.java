package com.franzzle.tooling.lipsync.api.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ValidUuidConstraintValidator.class)
public @interface ValidUuid {
    String message() default "Uuid is not properly formatted";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
