package com.programmers.library.view;

import com.programmers.library.validation.InputValidator;

import java.util.Scanner;

public class ConsoleInput  {

    private final Scanner scanner;

    public ConsoleInput() {
        this.scanner = new Scanner(System.in);
    }


    public Long selectNumber() {
        return InputValidator.checkNumber(scanner);
    }


    public String inputString() {
        return scanner.nextLine();
    }
}
