package org.example.server.entity;

import org.example.packet.dto.BookDto;
import org.example.server.entity.bookStatus.*;

public class Book {
    public int id; // 5000 미만
    public String name; // 100자 미만
    public String author; // 100자 미만
    public int pages; // 5000 미만
    public BookStatus status;

    public Book(BookDto bookDto) {
        this.name = bookDto.name;
        this.author = bookDto.author;
        this.pages = bookDto.pages;
        this.status = new CanBorrowStatus();
    }

    public Book(int id, String name, String author, int pages, String status, String endLoadTime) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.pages = pages;
        this.status = BookStatusType.valueOf(status).equals(BookStatusType.LOAD) ? new LoadStatus(endLoadTime) : BookStatusType.valueOf(status).getBookStatus();
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
