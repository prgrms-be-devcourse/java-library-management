package com.programmers.library.view.console;


import static java.lang.System.out;

public class ConsoleOutput  {

    public void write(String message) {
        out.print(message);
    }
    public void showMode() {
        out.print("Q. 모드를 선택해주세요.\n1. 일반 모드\n2. 테스트 모드\n\n> ");
    }
    public void showMenu() {
        out.print("\nQ. 사용할 기능을 선택해주세요.\n1. 도서 등록\n2. 전체 도서 목록 조회\n3. 제목으로 도서 검색\n4. 도서 대여\n5. 도서 반납\n6. 도서 분실\n7. 도서 삭제\n8. 종료\n\n> ");
    }
}
