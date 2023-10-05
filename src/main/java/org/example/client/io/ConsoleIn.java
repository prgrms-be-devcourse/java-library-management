package org.example.client.io;

import java.util.Scanner;

public class ConsoleIn implements In {
    private static final ConsoleIn instance = new ConsoleIn();
    private final Scanner scanner = new Scanner(System.in);

    private ConsoleIn() {
    }

    public static ConsoleIn getInstance() {
        return instance;
    }

    @Override
    public String scanLine() {
        return scanner.nextLine().trim();
    }
}
