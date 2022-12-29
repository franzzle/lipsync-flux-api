package com.franzzle.tooling.lipsync.api.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.UUID;

public class ValidUuidConstraintValidator implements ConstraintValidator<ValidUuid, String> {
    @Override
    public void initialize(ValidUuid constraintAnnotation) {
        //Left empty
    }

    @Override
    public boolean isValid(String uuid, ConstraintValidatorContext constraintValidatorContext) {
        return isUUID(uuid);
    }

    public static boolean isUUID(String input) {
        try {
            UUID.fromString(input);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
