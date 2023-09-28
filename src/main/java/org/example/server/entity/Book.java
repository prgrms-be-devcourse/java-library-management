package org.example.server.entity;

import java.time.LocalDateTime;

public class Book {
    public int id; // 5000 미만
    public String name; // 100자 이내
    public String author; // 100자 이내
    public int pages; // 5000 이내
    public BookState state;
    public LocalDateTime borrowTime = null;

    public Book(String name, String author, int pages) {
        this.name = name;
        this.author = author;
        this.pages = pages;
        this.state = BookState.CAN_BORROW;
        this.borrowTime = LocalDateTime.now();
    } // 새로 등록 할 때

    public Book(int id, String name, String author, int pages, BookState state, LocalDateTime borrowTime) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.pages = pages;
        this.state = state;
        this.borrowTime = borrowTime;
    }// 파일에서 로드 할 때

    @Override
    public String toString() {
        return "\n도서번호 : " + id + "\n" +
                "제목 : " + name + "\n"
                + "작가 이름 : " + author + "\n"
                + "페이지 수 : " + pages + " 페이지\n" +
                "상태 : " + state.getStatus() + "\n\n------------------------------\n";
    }
}
