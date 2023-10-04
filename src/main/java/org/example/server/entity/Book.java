package org.example.server.entity;

import org.example.server.entity.bookStatus.*;

public class Book {
    public int id; // 5000 미만
    public String name; // 100자 미만
    public String author; // 100자 미만
    public int pages; // 5000 미만
    public BookStatus status;

    public Book(String name, String author, int pages) {
        this.name = name;
        this.author = author;
        this.pages = pages;
        this.status = new CanBorrowStatus();
    }

    public Book(int id, String name, String author, int pages, String status, String endLoadTime) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.pages = pages;
        this.status = BookStatusType.valueOf(status).equals(BookStatusType.LOAD) ? new LoadStatus(endLoadTime) : BookStatusType.valueOf(status).getBookStatus();
    }

    @Override
    public String toString() {
        return System.lineSeparator() + "도서번호 : " + id + System.lineSeparator() +
                "제목 : " + name + System.lineSeparator()
                + "작가 이름 : " + author + System.lineSeparator()
                + "페이지 수 : " + pages + " 페이지" + System.lineSeparator() +
                "상태 : " + status.getBookStatusType().getStatusName() + System.lineSeparator() +
                System.lineSeparator() + "------------------------------" + System.lineSeparator();
    }

    public void borrow() {
        status.borrow();
        status = new BorrowedStatus();
    }

    public void restore() {
        status.restore();
        status = new LoadStatus(); // 5분후 대여 가능
    }

    public void lost() {
        status.lost();
        status = new LostStatus();
    }
}
