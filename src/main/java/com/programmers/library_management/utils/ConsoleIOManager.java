package com.programmers.library_management.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleIOManager {

    private final BufferedReader Buffered_Reader;

    public ConsoleIOManager() {
        this.Buffered_Reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public String getInput() throws IOException {
        print("> ");
        String input = Buffered_Reader.readLine();
        print("\n");
        return input;
    }

    public void printModeMenu() {
        String mode_Menu = """
                Q. 모드를 선택해주세요.
                1. 일반 모드
                2. 테스트 모드""";
        println(mode_Menu);
    }

    public void printFuncMenu() {
        String func_Menu = """
                Q. 사용할 기능을 선택해주세요.
                1. 도서 등록
                2. 전체 도서 목록 조회
                3. 제목으로 도서 검색
                4. 도서 대여
                5. 도서 반납
                6. 도서 분실
                7. 도서 삭제
                                
                0. 종료""";
        println(func_Menu);
    }

    public void printSystemMsg(String s) {
        println("[System] " + s);
    }

    public void printIOExceptionMsg() {
        printSystemMsg("잘못된 입력 오류입니다.");
    }

    public void printNumberFormatExceptionMsg() {
        printSystemMsg("잘못된 숫자 입력입니다.");
    }

    public void print(String s) {
        System.out.print(s);
    }

    public void println(String s) {
        System.out.println(s + "\n");
    }


}
