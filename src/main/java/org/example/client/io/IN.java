package org.example.client.io;

import java.util.Scanner;

// 입력만 관리하는 클래스
public interface IN {
    Scanner scanner = new Scanner(System.in);

    default String scanLine() {
        return scanner.nextLine().trim();
    }

    default int scanLineToInt() {
        return Integer.parseInt((scanner.nextLine().trim()));
    }
}
