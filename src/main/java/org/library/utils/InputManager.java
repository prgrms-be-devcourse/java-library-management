package org.library.utils;

import java.util.Scanner;

public class InputManager {
    private static final Scanner scanner = new Scanner(System.in);

    public int inputInt(){
        int intNum = scanner.nextInt();
        scanner.nextLine();
        return intNum;
    }

    public long inputLong(){
        long longNum = scanner.nextLong();
        scanner.nextLine();
        return longNum;
    }

    public String inputString(){
        return scanner.nextLine();
    }
}
