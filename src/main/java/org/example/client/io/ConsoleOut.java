package org.example.client.io;

import org.example.client.Validator;

import java.io.PrintStream;
import java.util.Scanner;

public class ConsoleOut implements Out {
    private static final ConsoleOut instance = new ConsoleOut();
    private final PrintStream printer = new PrintStream(System.out);

    private ConsoleOut() {
    }

    public static ConsoleOut getInstance() {
        return instance;
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
