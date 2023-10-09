package com.programmers.library.util;

import java.util.Scanner;

public class ScannerUtil {
    private final Scanner scanner;

    public ScannerUtil() {
        this.scanner = new Scanner(System.in);
    }

    public int inputInt() {
        while (true) {
            String input = scanner.nextLine().trim();

            try {
                return Integer.parseInt(input);
            } catch (Exception e) {
                System.out.println("[System] 🚨 올바른 값을 입력해 주세요 🚨");
            }
        }
    }

    public String inputString() {
        return scanner.nextLine().trim();
    }

    public void close() {
        scanner.close();
    }
}