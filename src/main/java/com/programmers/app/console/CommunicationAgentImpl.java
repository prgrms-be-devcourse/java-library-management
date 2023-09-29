package com.programmers.app.console;

import com.programmers.app.book.request.RequestBook;

public class CommunicationAgentImpl implements CommunicationAgent {

    private final ConsoleReader consoleReader;

    public CommunicationAgentImpl(ConsoleReader consoleReader) {
        this.consoleReader = consoleReader;
    }

    @Override
    public int instructModeSelection() {
        System.out.println(
                "Q. 모드를 선택해주세요.\n" +
                "1. 일반 모드\n" +
                "2. 테스트 모드"
        );
        return consoleReader.readInt();
    }

    @Override
    public int instructMenuSelection() {
        System.out.println(
                "Q. 사용할 기능을 선택해주세요.\n" +
                "1. 도서 등록\n" +
                "2. 전체 도서 목록 조회\n" +
                "3. 제목으로 도서 검색\n" +
                "4. 도서 대여\n" +
                "5. 도서 반납\n" +
                "6. 도서 분실\n" +
                "7. 도서 삭제"
        );
        return consoleReader.readInt();
    }

    @Override
    public RequestBook instructRegister() {
        System.out.println("Q. 등록할 도서 제목을 입력하세요.");
        String title = consoleReader.readString();

        System.out.println("Q. 작가 이름을 입력하세요.");
        String author = consoleReader.readString();

        System.out.println("Q. 페이지 수를 입력하세요.");
        int totalPages = consoleReader.readInt();

        return new RequestBook(title, author, totalPages);
    }

    @Override
    public String instructFindTitle() {
        System.out.println("Q. 검색할 도서 제목 일부를 입력하세요.");
        return consoleReader.readString();
    }

    @Override
    public long instructBorrow() {
        System.out.println("Q. 대여할 도서번호를 입력하세요");
        return consoleReader.readLong();
    }

    @Override
    public long instructReturn() {
        System.out.println("Q. 대여할 도서번호를 입력하세요");
        return consoleReader.readLong();
    }

    @Override
    public long instructReportLost() {
        System.out.println("Q. 반납할 도서번호를 입력하세요");
        return consoleReader.readLong();
    }

    @Override
    public long instructDelete() {
        System.out.println("Q. 삭제 처리할 도서번호를 입력하세요");
        return consoleReader.readLong();
    }

    @Override
    public void print(String message) {
        System.out.println(message);
    }
}
