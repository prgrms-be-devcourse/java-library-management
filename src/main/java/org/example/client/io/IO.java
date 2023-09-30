package org.example.client.io;

import java.io.PrintStream;
import java.util.Scanner;

// 입출력 모두 관리하는 클래스
public class IO {
    private static Scanner scanner = new Scanner(System.in);
    private PrintStream printer = new PrintStream(System.out);

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
