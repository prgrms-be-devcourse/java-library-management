package com.example.library.io;

import com.example.library.validation.InputValidator;

import java.util.Scanner;

public class ConsoleInput {
    Scanner scanner;

    public ConsoleInput() {
        scanner = new Scanner(System.in);
    }

    //모드 선택
    public int selectMode() {
        return InputValidator.isModeNumber(scanner.nextLine());
    }

    public int selectMenu() {
        return InputValidator.isMenuNumber(scanner.nextLine());
    }

    public String enterTitle() {
        return scanner.nextLine();
    }

    public String enterWriter() {
        return scanner.nextLine();
    }

    public int enterPageNumber() {
        return InputValidator.isNumber(scanner.nextLine());
    }

    public int enterBookId() {
        return InputValidator.isNumber(scanner.nextLine());
    }

}

