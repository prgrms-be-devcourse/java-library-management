package com.programmers.app.console;

import java.util.Scanner;

public class ConsoleReader {

    private final Scanner sc;

    public ConsoleReader() {
        sc = new Scanner(System.in);
    }

    int readInt() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (RuntimeException e) {
                System.out.println("[System] 숫자를 입력해주세요.");
            }
        }
    }

    String readString() {
        String input = sc.nextLine();

        while (input.strip().isBlank()) {
            input = sc.nextLine();
        }

        return input;
    }
}
