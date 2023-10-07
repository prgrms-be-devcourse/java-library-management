package com.programmers.infrastructure.IO.validator;

import com.programmers.exception.checked.InputValidationException;

public class InputValidator {
    private static final int MAX_INPUT_LENGTH = 100;

    public void validateString(String input) throws InputValidationException {
        // TODO: trim 이중
        // TODO 클라이언트에서 검증한 값을 서버에서 검증 해야 한다. 여기서는 클라에서만 한다.
        if (input.length() > MAX_INPUT_LENGTH || input.trim().isEmpty()) {
            throw new InputValidationException();
        }
    }

}
