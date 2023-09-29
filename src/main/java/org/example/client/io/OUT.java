package org.example.client.io;

import java.io.PrintStream;

public interface OUT {
    PrintStream printer = new PrintStream(System.out);

    default void print(String string) {
        printer.print(string);
    }
}
