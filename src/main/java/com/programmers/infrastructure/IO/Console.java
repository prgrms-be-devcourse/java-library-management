package com.programmers.infrastructure.IO;

import java.util.Scanner;

public class Console {
    private static final Console INSTANCE = new Console();
    private final Scanner scanner = new Scanner(System.in);

    private Console() {}

    public static Console getInstance() {
        return INSTANCE;
    }

    public String getInput() {
        return scanner.nextLine();
    }

    public void printToConsole(String message) {
        System.out.println(message);
    }
}

