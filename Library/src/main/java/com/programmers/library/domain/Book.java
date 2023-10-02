package com.programmers.library.domain;


import static com.programmers.library.domain.BookStatusType.RENTABLE;

public class Book {
    private final Long bookId;
    private final String title;
    private final String author;
    private final Integer page;
    private BookStatusType bookStatus;

    public Book(Long bookId, String title, String author, Integer page) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.page = page;
        this.bookStatus = RENTABLE;
    }
    public Book(Long bookId, String title, String author, Integer page, BookStatusType bookStatus){
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.page = page;
        this.bookStatus = bookStatus;
    }

    public void updateBookStatus(BookStatusType bookStatus){
        this.bookStatus = bookStatus;
    }
    public Long getBookId() {
        return bookId;
    }
    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public Integer getPage() {
        return page;
    }
    public BookStatusType getBookStatus() {
        return bookStatus;
    }
}
