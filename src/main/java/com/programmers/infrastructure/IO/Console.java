package com.programmers.infrastructure.IO;

import com.programmers.common.Validator;
import com.programmers.exception.checked.ValidationException;
import com.programmers.presentation.UserInteraction;

import java.util.Scanner;

public class Console implements UserInteraction {
    private final Scanner scanner = new Scanner(System.in);
    private final Validator<String> validator;

    public Console(Validator validator) {
        this.validator = validator;
    }

    @Override
    public void displayMessage(String message) {
        System.out.println(message);
    }

    @Override
    public String collectUserInput() {
        String input = scanner.nextLine().trim();
        // TODO: 멈추는 조건 설정
        try{
            validator.validate(input);
        } catch (ValidationException e) {
            displayMessage(e.getErrorCode().getMessage());
            return collectUserInput();
        }
        return input;
    }
}

