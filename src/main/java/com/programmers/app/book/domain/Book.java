package com.programmers.app.book.domain;

import java.time.LocalDateTime;

import com.programmers.app.book.dto.BookJSON;

public class Book {

    private static final int MINUTES_FOR_BOOK_ARRANGEMENT = 5;

    private final int bookNumber;
    private final String title;
    private final String author;
    private final int totalPages;
    private final BookStatus status;
    private final LocalDateTime arrangementBegunAt;

    public Book(
            int bookNumber,
            String title,
            String author,
            int totalPages,
            BookStatus status,
            LocalDateTime arrangementBegunAt) {
        this.bookNumber = bookNumber;
        this.title = title;
        this.author = author;
        this.totalPages = totalPages;
        this.status = status;
        this.arrangementBegunAt = arrangementBegunAt;
    }

    public int getBookNumber() {
        return bookNumber;
    }

    public boolean containsInTitle(String title) {
        return this.title.contains(title);
    }

    public boolean isInPlace() {
        return status == BookStatus.IN_PLACE;
    }

    public boolean isBorrowed() {
        return status == BookStatus.BORROWED;
    }

    public boolean isLost() {
        return status == BookStatus.LOST;
    }

    public boolean isOnArrangement() {
        return status == BookStatus.ON_ARRANGEMENT;
    }

    public boolean isDoneArranging() {
        if (!this.isOnArrangement()) {
            return false;
        }
        LocalDateTime completedAt = arrangementBegunAt.plusMinutes(MINUTES_FOR_BOOK_ARRANGEMENT);
        return completedAt.isBefore(LocalDateTime.now());
    }

    public Book generateStatusUpdatedBook(BookStatus status) {
        return new Book(bookNumber, title, author, totalPages, status, null);
    }

    public Book generateReturnedBook() {
        return new Book(bookNumber, title, author, totalPages, BookStatus.ON_ARRANGEMENT, LocalDateTime.now());
    }

    public BookJSON toBookJSON() {
        return new BookJSON(bookNumber, title, author, totalPages, status, arrangementBegunAt);
    }

    @Override
    public String toString() {
        return "도서 번호 : " + bookNumber + "\n" +
                "제목 : " + title + '\n' +
                "작가 이름 : " + author + '\n' +
                "페이지 수 : " + totalPages + " 페이지\n" +
                "상태 : " + status.getStatus() + "\n" +
                "------------------------------";
    }
}
