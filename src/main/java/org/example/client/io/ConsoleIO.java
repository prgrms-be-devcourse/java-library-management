package org.example.client.io;

import java.io.PrintStream;
import java.util.Scanner;

public class ConsoleIO implements IO {
    private static final ConsoleIO instance = new ConsoleIO();
    private final Scanner scanner = new Scanner(System.in);
    private final PrintStream printer = new PrintStream(System.out);

    private ConsoleIO() {
    }

    public static ConsoleIO getInstance() {
        return instance;
    }

    @Override
    public String scanLine() {
        return scanner.nextLine().trim();
    }

    @Override
    public void print(String string) {
        printer.print(string);
    }

    @Override
    public void println(String string) {
        printer.println(string);
    }
}
