package io;

import service.GeneralService;
import service.Service;
import service.TestService;

import java.util.Scanner;

public class Console {

    Scanner scanner = new Scanner(System.in);

    private Service service;

    public void selectMode() {
        System.out.println("Q. 모드를 선택해주세요.");
        System.out.println("1. 일반 모드");
        System.out.println("2. 테스트 모드");
        System.out.println();

        System.out.print("> ");

        int mode = scanner.nextInt();
        printMode(mode);
    }

    public void printMode(int mode) {
        if (mode == 1) {
            System.out.println("[System] 일반 모드로 애플리케이션을 실행합니다.");
            service = new GeneralService();
        } else if (mode == 2) {
            System.out.println("[System] 테스트 모드로 애플리케이션을 실행합니다.");
            service = new TestService();
        } else {
            throw new RuntimeException("[System] 잘못된 입력입니다.");
        }
    }
}
