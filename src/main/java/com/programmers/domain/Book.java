package com.programmers.domain;

import java.util.List;

public class Book {
    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", totalPageNumber=" + totalPageNumber +
                ", bookStatus=" + bookStatus +
                '}';
    }

    private Long bookId;
    private String title;
    private String author;
    private int totalPageNumber;
    private BookStatus bookStatus;

    public Book(String title, String author, int totalPageNumber) {
        this.title = title;
        this.author = author;
        this.totalPageNumber = totalPageNumber;
        this.bookStatus = BookStatus.AVAILABLE;
    }

    public Book(Long bookId, String title, String author, int totalPageNumber, BookStatus bookStatus) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.totalPageNumber = totalPageNumber;
        this.bookStatus = bookStatus;
    }

    public static Book enrollingBook(String title, String author, int totalPageNumber){
        return new Book(title, author, totalPageNumber);
    }

    public boolean searchByTitle(String title){
        return this.title.contains(title);
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

    public int getTotalPageNumber() {
        return totalPageNumber;
    }

    public BookStatus getBookStatus() {
        return bookStatus;
    }

    public void rentBook(){
        this.bookStatus = BookStatus.ONGOING;
    }

    public void loseBook(){
        this.bookStatus = BookStatus.LOST;
    }

    public void deleteBook() {
        this.bookStatus = BookStatus.DELETED;
    }

    public void settingId(long bookId) {
        this.bookId = bookId;
    }

    public void returnBook() {
        if(!(this.bookStatus == BookStatus.ONGOING)) throw new RuntimeException("[System] 대여중인 가능한 도서입니다.");
        this.bookStatus = BookStatus.AVAILABLE;
    }

    public boolean checkRentStatus() {
        return this.bookStatus == BookStatus.AVAILABLE;
    }

    public boolean checkOngoing() {
        return this.bookStatus == BookStatus.ONGOING;
    }

    public boolean checkingAlreadyLose() {
        return this.bookStatus == BookStatus.LOST;
    }

    public boolean checkingAlreadyDeleted() {
        return this.bookStatus == BookStatus.DELETED;
    }
}
