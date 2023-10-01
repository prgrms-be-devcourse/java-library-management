package dev.course.manager;

import dev.course.exception.ConsoleIOFailureException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleManager {

    private final BufferedReader bufferedReader;

    public ConsoleManager() {
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    public BufferedReader getBufferedReader() {
        return this.bufferedReader;
    }

    public int getInteger() {
        return Integer.parseInt(getString());
    }

    public String getString() {
        try {
            return getBufferedReader().readLine();
        } catch (IOException e) {
            throw new ConsoleIOFailureException("[System] 콘솔에 입력된 값을 받아오는데 실패했습니다.\n");
        }
    }

    public Long getLong() {
        return Long.parseLong(getString());
    }

    public void printMode() {

        System.out.println("0. 모드를 선택해주세요.");
        System.out.println("1. 일반 모드");
        System.out.println("2. 테스트 모드\n");
    }

    public void printMenu() {

        System.out.println("0. 사용할 기능을 선택해주세요.");
        System.out.println("1. 도서 등록");
        System.out.println("2. 전체 도서 목록 조회");
        System.out.println("3. 제목으로 도서 검색");
        System.out.println("4. 도서 대여");
        System.out.println("5. 도서 반납");
        System.out.println("6. 도서 분실");
        System.out.println("7. 도서 삭제");
        System.out.println("8. 시스템 종료\n");
    }
}
