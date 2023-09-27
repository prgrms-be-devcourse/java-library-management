package com.programmers.infrastructure.IO.validator;

import com.programmers.common.Validator;
import com.programmers.exception.checked.InputValidationException;

import static com.programmers.exception.ErrorCode.INVALID_INPUT;

public class InputValidator implements Validator<String> {
    private static final int MAX_INPUT_LENGTH = 100;

    @Override
    public void validate(String input) throws InputValidationException {
        // TODO: trim 이중
        if (input.length() > MAX_INPUT_LENGTH || input.trim().isEmpty()) {
            throw new InputValidationException(INVALID_INPUT);
        }
    }
}
