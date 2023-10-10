package com.programmers.infrastructure.IO.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.programmers.exception.checked.InputValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InputValidatorTest {

    private InputValidator inputValidator;

    @BeforeEach
    void setUp() {
        inputValidator = new InputValidator();
    }

    @Test
    @DisplayName("유효하지 않은 값 - [max length]")
    void testValidateString_MaxLength() {
        String input = new String(new char[101]).replace('\0', 'a');
        // length 101

        assertThrows(InputValidationException.class, () -> inputValidator.validateString(input));
    }

    @Test
    @DisplayName("유효하지 않은 값 - [empty input]")
    void testValidateString_Empty() {
        String input = "";

        assertThrows(InputValidationException.class, () -> inputValidator.validateString(input));
    }

    @Test
    @DisplayName("유효하지 않은 값 - [only spaces]")
    void testValidateString_OnlySpaces() {
        String input = "    ";

        assertThrows(InputValidationException.class, () -> inputValidator.validateString(input));
    }

    @Test
    @DisplayName("유효한 값")
    void testValidateString_Valid() {
        String input = "Valid Input";

        assertDoesNotThrow(() -> inputValidator.validateString(input));
    }
}
