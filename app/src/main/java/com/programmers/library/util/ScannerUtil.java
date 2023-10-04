package com.programmers.library.util;

import java.util.Scanner;

public class ScannerUtil {
    private final static ScannerUtil instance = new ScannerUtil();
    private final Scanner scanner = new Scanner(System.in);

    private ScannerUtil() {
    }

    public int inputInt() {
        int result = 0;
        String input = scanner.nextLine().trim();

        try {
            result = Integer.parseInt(input);
        } catch (Exception e) {
            System.out.println("[System] 🚨 올바른 값을 입력해 주세요 🚨");
        }

        return result;
    }

    public String inputString() {
        return scanner.nextLine().trim();
    }

    public void close() {
        scanner.close();
    }

    public static ScannerUtil getInstance() {
        return instance;
    }
}