package com.programmers.library.domain;

import static com.programmers.library.domain.BookStatusType.*;

public class Book {
    private final Long bookId;
    private final String title;
    private final String author;
    private final Integer page;
    private BookStatusType bookStatus;

    private Book(Long bookId, String title, String author, Integer page, BookStatusType bookStatus) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.page = page;
        this.bookStatus = bookStatus;
    }

    public static Book createRentableBook(Long bookId, String title, String author, Integer page) {
        return new Book(bookId, title, author, page, RENTABLE);
    }

    public static Book createBookWithStatus(Long bookId, String title, String author, Integer page, BookStatusType bookStatus){
        return new Book(bookId, title, author, page, bookStatus);
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
