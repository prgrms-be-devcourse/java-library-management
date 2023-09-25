package io;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Input {

    static Scanner scanner = new Scanner(System.in);
    public static int inputNumber() {
        try {
            int number = scanner.nextInt();
            scanner.nextLine();
            return number;
        } catch (InputMismatchException e) {
            scanner.nextLine();
            return 0;
        }
    }

    public static String inputString() {
        return scanner.nextLine();
    }
}