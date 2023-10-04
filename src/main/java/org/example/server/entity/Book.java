package org.example.server.entity;

import java.time.LocalDateTime;
import java.util.Optional;

public class Book {
    public int id; // 5000 미만
    public String name; // 100자 미만
    public String author; // 100자 미만
    public int pages; // 5000 미만
    public String state;
    public Optional<LocalDateTime> endLoadTime;

    public Book(String name, String author, int pages) {
        this.name = name;
        this.author = author;
        this.pages = pages;
        this.state = BookState.CAN_BORROW.name();
        this.endLoadTime = Optional.empty();
    }

    public Book(int id, String name, String author, int pages, String state, String endLoadTime) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.pages = pages;
        this.state = state;
        this.endLoadTime = endLoadTime.isEmpty() ? Optional.empty() : Optional.of(LocalDateTime.parse(endLoadTime));
    }

    @Override
    public String toString() {
        return System.lineSeparator() + "도서번호 : " + id + System.lineSeparator() +
                "제목 : " + name + System.lineSeparator()
                + "작가 이름 : " + author + System.lineSeparator()
                + "페이지 수 : " + pages + " 페이지" + System.lineSeparator() +
                "상태 : " + BookState.valueOf(state).getStatus() + System.lineSeparator() +
                System.lineSeparator() + "------------------------------" + System.lineSeparator();
    }
}
