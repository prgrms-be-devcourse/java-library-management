package io;

import exception.InputFormatException;
import repository.GeneralRepository;
import repository.TestRepository;
import service.Service;
import service.TotalService;

import java.util.Scanner;

public class Console {

    private static final Scanner scanner = new Scanner(System.in);

    private static final int GENERAL = 1;
    private static final int TEST = 2;

    private Service service;

    public void selectMode() {
        System.out.println("Q. 모드를 선택해주세요.");
        System.out.println("1. 일반 모드");
        System.out.println("2. 테스트 모드");
        System.out.println();

        System.out.print("> ");

        int input = scanner.nextInt();

        printMode(input);

        scanner.close();
    }

    public void printMode(int input) {
        if (input == GENERAL) {
            System.out.println("[System] 일반 모드로 애플리케이션을 실행합니다.");
            service = new TotalService(new GeneralRepository());
            service.load();
        } else if (input == TEST) {
            System.out.println("[System] 테스트 모드로 애플리케이션을 실행합니다.");
            service = new TotalService(new TestRepository());
            service.load();
        } else {
            throw new InputFormatException("잘못된 입력입니다.");
        }
    }
}
