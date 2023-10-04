package com.programmers.infrastructure.IO.validator;

import com.programmers.exception.checked.InputValidationException;

public class InputValidator {
    private static final int MAX_INPUT_LENGTH = 100;

    public void validateString(String input) throws InputValidationException {
        // TODO: trim 이중
        if (input.length() > MAX_INPUT_LENGTH || input.trim().isEmpty()) {
            throw new InputValidationException();
        }
    }

}
