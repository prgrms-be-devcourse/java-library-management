package com.programmers.app.console;

import java.util.List;

import com.programmers.app.book.domain.Book;
import com.programmers.app.book.dto.BookRequest;
import com.programmers.app.menu.Menu;
import com.programmers.app.mode.Mode;

public class CommunicationAgent {

    private final ConsoleReader consoleReader;

    public CommunicationAgent(ConsoleReader consoleReader) {
        this.consoleReader = consoleReader;
    }

    public int instructModeSelection() {
        System.out.println(
                "Q. 모드를 선택해주세요.\n" +
                "1. " + Mode.NORMAL.displayName() + "\n" +
                "2. " + Mode.TEST.displayName());
        return consoleReader.readInt();
    }

    public int instructMenuSelection() {
        System.out.println(
                "Q. 사용할 기능을 선택해주세요.\n" +
                "0. 시스템 종료\n" +
                "1. 도서 등록\n" +
                "2. 전체 도서 목록 조회\n" +
                "3. 제목으로 도서 검색\n" +
                "4. 도서 대여\n" +
                "5. 도서 반납\n" +
                "6. 도서 분실\n" +
                "7. 도서 삭제");
        return consoleReader.readInt();
    }

    public BookRequest instructRegister() {
        System.out.println("[System] 도서 등록 메뉴로 넘어갑니다.");
        System.out.println("Q. 등록할 도서 제목을 입력하세요.");
        String title = consoleReader.readString();

        System.out.println("Q. 작가 이름을 입력하세요.");
        String author = consoleReader.readString();

        System.out.println("Q. 페이지 수를 입력하세요.");
        int totalPages = consoleReader.readInt();

        return new BookRequest(title, author, totalPages);
    }

    public String instructFindTitle() {
        System.out.println("[System] 제목으로 도서 검색 메뉴로 넘어갑니다.");
        System.out.println("Q. 검색할 도서 제목 일부를 입력하세요.");
        return consoleReader.readString();
    }

    public int instructMenuAction(Menu menu) {
        System.out.println("[System] 도서 " + menu.displayName() + " 메뉴로 넘어갑니다.");
        System.out.println("Q. " + menu.displayName() + " 처리할 도서번호를 입력하세요.");
        return consoleReader.readInt();
    }

    public void printModeSelected(String modeName) {
        System.out.println("[System] " + modeName + "로 애플리케이션을 실행합니다.");
    }

    public void printOperationCompleted(Menu menu) {
        if (menu == Menu.REGISTER) {
            System.out.println("[System] 도서 등록이 완료되었습니다.");
        }

        else {
            System.out.println("[System] 도서가 " + menu.displayName() + " 처리 되었습니다.");
        }
    }

    public void printFindResult(List<Book> books) {
        books.forEach(b -> System.out.println(b.toString()));
        System.out.println("[System] 도서 목록 끝");
    }

    public void printSearchResult(List<Book> books) {
        books.forEach(b -> System.out.println(b.toString()));
        System.out.println("[System] 검색된 도서 끝");
    }

    public void printExitMessage() {
        System.out.println("[System] 시스템을 종료합니다.");
    }

    public void printError(Exception e) {
        System.out.println(e.getMessage());
    }

    public void printConfigError() {
        System.out.println("Failed to read file. App running Test Mode");
    }
}
