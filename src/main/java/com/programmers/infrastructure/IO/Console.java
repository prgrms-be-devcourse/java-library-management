package com.programmers.infrastructure.IO;

import com.programmers.exception.checked.InputValidationException;
import com.programmers.exception.checked.ValidationException;
import com.programmers.infrastructure.IO.validator.InputValidator;
import java.util.InputMismatchException;
import java.util.Scanner;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Console {

    private final Scanner scanner;
    private final InputValidator validator;

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public String collectUserInput() {
        System.out.print("> ");
        String input = scanner.nextLine().trim();
        // TODO: 멈추는 조건 설정
        try {
            validator.validateString(input);
        } catch (ValidationException e) {
            displayMessage(e.getErrorCode().getMessage());
            return collectUserInput();
        }
        return input;
    }

    public Long collectUserLongInput() {
        System.out.print("> ");
        try {
            Long input = scanner.nextLong();
            scanner.nextLine();  // 버퍼를 지우기 위해 호출
            return input;
        } catch (InputMismatchException e) {
            scanner.nextLine();  // 버퍼를 지우기 위해 호출
            displayMessage(new InputValidationException().getMessage());
            return collectUserLongInput();
        }
    }

    public Integer collectUserIntegerInput() {
        System.out.print("> ");
        try {
            int input = scanner.nextInt();
            scanner.nextLine();  // 버퍼를 지우기 위해 호출
            return input;
        } catch (InputMismatchException e) {
            scanner.nextLine();  // 버퍼를 지우기 위해 호출
            displayMessage(new InputValidationException().getMessage());
            return collectUserIntegerInput();
        }
    }

}

