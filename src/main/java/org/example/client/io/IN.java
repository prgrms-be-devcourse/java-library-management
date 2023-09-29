package org.example.client.io;

import java.util.Scanner;

public interface IN {
    Scanner scanner = new Scanner(System.in);

    default String scanLine() {
        return scanner.nextLine();
    }

    default int scanLineToInt() {
        return Integer.parseInt((scanner.nextLine()));
    }
}
