package com.programmers.library.utils;

import java.util.Scanner;

public class ConsoleIO {
    private final Scanner scanner;

    public ConsoleIO() {
        scanner = new Scanner(System.in);
    }

    public String getStringInput(String message) {   // 문자열 입력
        printMessage(message);
        System.out.print("> ");
        return scanner.nextLine();
    }

    public int getIntInput(String message) {     // 정수 입력
        printMessage(message);
        System.out.print("> ");
        int input = scanner.nextInt();
        scanner.nextLine();
        return input;
    }

    public void printMessage(String message) {  // 메시지 출력
        System.out.println(message);
    }
}
