package org.example.client.io;

import java.io.PrintStream;
import java.util.Scanner;

public class IO {
    private static final IO instance = new IO();
    private final Scanner scanner = new Scanner(System.in);
    private final PrintStream printer = new PrintStream(System.out);

    private IO() {
    }

    public static IO getInstance() {
        return instance;
    }

    public String scanLine() {
        return scanner.nextLine().trim();
    }

    public void print(String string) {
        printer.print(string);
    }

    public void println(String string) {
        printer.println(string);
    }
}
