package com.programmers.librarymanagement.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleIo {

    private final BufferedReader br;

    public ConsoleIo() {
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    public void printSelectMode() {
        System.out.println("Q. 모드를 선택해주세요. \n" +
                "1. 일반 모드 \n" +
                "2. 테스트 모드 \n");
    }

    public void printSelectFun() {
        System.out.println("Q. 사용할 기능을 선택해주세요. \n" +
                "1. 도서 등록 \n" +
                "2. 전체 도서 목록 조회 \n" +
                "3. 제목으로 도서 검색 \n" +
                "4. 도서 대여 \n" +
                "5. 도서 반납 \n" +
                "6. 도서 분실 \n" +
                "7. 도서 삭제 \n");
    }

    public String getInput() {

        System.out.print("> ");

        String input;
        try {
            input = br.readLine().strip();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println();

        return input;
    }
}
