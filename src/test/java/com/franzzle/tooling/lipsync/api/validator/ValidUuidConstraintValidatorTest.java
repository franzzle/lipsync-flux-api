package com.franzzle.tooling.lipsync.api.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class ValidUuidConstraintValidatorTest {

    private ValidUuidConstraintValidator validUuidConstraintValidator;
    private ConstraintValidatorContext constraintValidatorContext;

    @BeforeEach
    void setUp() {
        validUuidConstraintValidator = new ValidUuidConstraintValidator();
        constraintValidatorContext = mock(ConstraintValidatorContext.class);
    }

    @Test
    void testIsValid() {
        assertTrue(validUuidConstraintValidator.isValid("3d490a70-94ce-4d15-9494-5248280eebb3", constraintValidatorContext));
        assertFalse(validUuidConstraintValidator.isValid("not-valid-uuid", constraintValidatorContext));
    }

    @Test
    void testIsUUID() {
        assertTrue(ValidUuidConstraintValidator.isUUID("3d490a70-94ce-4d15-9494-5248280eebb3"));
        assertFalse(ValidUuidConstraintValidator.isUUID("not-valid-uuid"));
    }
}
