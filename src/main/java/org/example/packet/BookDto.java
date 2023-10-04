package org.example.packet;

import org.example.server.entity.Book;

public class BookDto {
    public int id; // 5000 미만
    public String name; // 100자 미만
    public String author; // 100자 미만
    public int pages; // 5000 미만
    public String status;

    public BookDto(Book book) {
        this.id = book.id;
        this.name = book.name;
        this.author = book.author;
        this.pages = book.pages;
        this.status = book.status.getBookStatusType().getStatusName();
    }

    public BookDto() {
    }

    @Override
    public String toString() {
        return System.lineSeparator() + "도서번호 : " + id + System.lineSeparator() +
                "제목 : " + name + System.lineSeparator()
                + "작가 이름 : " + author + System.lineSeparator()
                + "페이지 수 : " + pages + " 페이지" + System.lineSeparator() +
                "상태 : " + status + System.lineSeparator() +
                System.lineSeparator() + "------------------------------" + System.lineSeparator();
    }
}
