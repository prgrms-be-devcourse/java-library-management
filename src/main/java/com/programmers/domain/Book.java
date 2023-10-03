package com.programmers.domain;

import java.util.List;
import java.util.Objects;

public class Book {
    private Long bookId;

    private String title;
    private String author;
    private int totalPageNumber;
    private BookStatus bookStatus;

    private Book(String title, String author, int totalPageNumber) {
        this(null, title, author, totalPageNumber, BookStatus.AVAILABLE);
    }

    public Book(Long bookId, String title, String author, int totalPageNumber, BookStatus bookStatus) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.totalPageNumber = totalPageNumber;
        this.bookStatus = bookStatus;
    }

    public static Book enrollingBook(String title, String author, int totalPageNumber) {
        return new Book(title, author, totalPageNumber);
    }

    public boolean searchByTitle(String title) {
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

    public void rentBook() {
        this.bookStatus = BookStatus.ONGOING;
    }

    public void loseBook() {
        this.bookStatus = BookStatus.LOST;
    }

    public void deleteBook() {
        this.bookStatus = BookStatus.DELETED;
    }

    public void settingId(long bookId) {
        this.bookId = bookId;
    }

    public void returnBook() {
        if (!(this.bookStatus == BookStatus.ONGOING)) throw new RuntimeException("[System] 대여중인 가능한 도서입니다.");
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book book)) return false;
        return totalPageNumber == book.totalPageNumber && bookId.equals(book.bookId) && title.equals(book.title) && author.equals(book.author) && bookStatus == book.bookStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, title, author, totalPageNumber, bookStatus);
    }

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
}
