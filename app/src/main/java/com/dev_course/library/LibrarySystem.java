package com.dev_course.library;

import com.dev_course.io_module.LibraryReader;

public class LibrarySystem {
    LibraryReader reader;

    public LibrarySystem(LibraryReader reader) {
        this.reader = reader;
    }

    public void run() {
        setMode();

        selectScreen();

        exit();
    }

    private void setMode() {
        System.out.println("""
                  Q. 모드를 선택해주세요.
                  1. 일반 모드
                  2. 테스트 모드
                """);

        // TODO : SystemMode 따른 도서관 클래스의 Strategy 변경이 필요
    }

    private void selectScreen() {
        // TODO : 선택 화면에서 세부 기능으로 넘어가는 부분을 어떻게 리팩토링할지 고민
        // TODO : Console 출력 코드를 어떻게 분리할지 고민

        System.out.println("""
                Q. 사용할 기능을 선택해주세요.
                0. 프로그램 종료
                1. 도서 등록
                2. 전체 도서 목록 조회
                3. 제목으로 도서 검색
                4. 도서 대여
                5. 도서 반납
                6. 도서 분실
                7. 도서 삭제
                """);

        String input = reader.readOrElse("-1");

        switch (input) {
            case "0" -> {
                return;
            }
            case "1" -> createBook();
            case "2" -> listBooks();
            case "3" -> findBookByTitle();
            default -> {
                System.out.println("[System] 사용할 수 없는 기능입니다.");
            }
        }

        selectScreen();
    }

    private void exit() {
        System.out.println("[System] 프로그램을 종료합니다.");
    }

    private void createBook() {
        System.out.println("[System] 도서 등록 메뉴로 넘어갑니다.");
    }

    private void listBooks() {
        System.out.println("[System] 전체 도서 목록입니다.");
    }

    private void findBookByTitle() {
        System.out.println("[System] 제목으로 도서 검색 메뉴로 넘어갑니다.");
    }
}
