package org.example.client.io;

import java.io.PrintStream;

// 출력만 관리하는 클래스
public interface OUT {
    PrintStream printer = new PrintStream(System.out);

    default void print(String string) {
        printer.print(string);
    }
}
