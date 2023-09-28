package org.example.server.entity;

import java.time.LocalDateTime;

public class Book {
    public int id;
    public String name;
    public String author;
    public int pages;
    public BookState state;
    public LocalDateTime borrowTime = null;

    public Book(RequestBookDto requestBookDto) {//        id
        this.name = requestBookDto.name;
        this.author = requestBookDto.author;
        this.pages = requestBookDto.pages;
        this.state = BookState.CAN_BORROW;
        this.borrowTime = LocalDateTime.now();
    } // 새로 등록 할 때

    public Book(String name, String author, int pages, int id, String name1, String author1, int pages1, BookState state, LocalDateTime borrowTime) {
        this.id = id;
        this.name = name1;
        this.author = author1;
        this.pages = pages1;
        this.state = state;
        this.borrowTime = borrowTime;
    }// 파일에서 로드 할 때

    @Override
    public String toString() {
        return "도서번호 : " + id + "\n" +
                "제목 : " + name + "\n"
                + "작가 이름 : " + author + "\n"
                + "페이지 수 : " + pages + " 페이지\n" +
                "상태 : " + state.getStatus() + "\n" +
                "\n" +
                "------------------------------";
    }
}
