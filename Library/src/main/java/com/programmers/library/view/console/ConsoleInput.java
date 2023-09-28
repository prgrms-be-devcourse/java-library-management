package com.programmers.library.view.console;

import com.programmers.library.view.Input;

import java.util.Scanner;

public class ConsoleInput implements Input {

    private final Scanner scanner;

    public ConsoleInput() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public Long selectNumber() {
        return Long.valueOf(scanner.nextLine());
    }

    @Override
    public String inputString() {
        return scanner.nextLine();
    }
}
