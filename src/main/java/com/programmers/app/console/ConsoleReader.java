package com.programmers.app.console;

import java.util.Scanner;

public class ConsoleReader {

    private final Scanner sc;

    public ConsoleReader() {
        sc = new Scanner(System.in);
    }

    int readInt() {
        return sc.nextInt();
    }

    long readLong() {
        return sc.nextLong();
    }

    String readString() {
        return sc.nextLine();
    }
}
