package com.programmers.library.util;

import java.util.Scanner;

public class ScannerUtil {

    public static int inputInt(Scanner scanner) {
        int result = 0;
        String input = scanner.nextLine().trim();

        try {
            result = Integer.parseInt(input);
        } catch (Exception e) {
            System.out.println("[System] 🚨 올바른 값을 입력해 주세요 🚨");
        }

        return result;
    }

    public static String inputString(Scanner scanner) {
        return scanner.nextLine().trim();
    }

}
