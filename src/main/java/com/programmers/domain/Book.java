package com.programmers.domain;

import com.programmers.common.ErrorMessages;
import com.programmers.provider.BookIdProvider;

import java.util.Objects;

public class Book {
    private final int id;
    private final String title;
    private final String author;
    private final int pages;
    private BookState state;

    @Override
    public String toString() {
        String NEWLINE = System.lineSeparator();
        return "도서번호 : " + id + NEWLINE +
                "제목 : " + title + NEWLINE +
                "작가 이름 : " + author + NEWLINE +
                "페이지 수 : " + pages + NEWLINE +
                "상태 : " + state.getMessage() + NEWLINE;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Book book = (Book) obj;
        return id == book.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Book(String title, String author, int pages) {
        this.id = BookIdProvider.generateBookId();
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.state = BookState.AVAILABLE;
    }

    public Book(int id, String title, String author, int pages, BookState state) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.state = state;
    }

    public Book(String[] bookArr) {
        if (bookArr.length != 5) throw new IllegalArgumentException(ErrorMessages.CSV_FORMAT_ERROR.toString());
        this.id = Integer.parseInt(bookArr[0]);
        this.title = bookArr[1];
        this.author = bookArr[2];
        this.pages = Integer.parseInt(bookArr[3]);
        this.state = BookState.valueOf(bookArr[4]);
        if (this.state == BookState.ORGANIZING) this.state = BookState.AVAILABLE;
    }

    public int getId() {
        return id;
    }

    public BookState getState() {
        return state;
    }

    public void setToAvailable() {
        this.state = BookState.AVAILABLE;
    }

    public void setToRented() {
        this.state = BookState.RENTED;
    }

    public void setToLost() {
        this.state = BookState.LOST;
    }

    public void setToOrganizing() {
        this.state = BookState.ORGANIZING;
    }

    public String joinInfo(String separator) {
        return this.id + separator
                + this.title + separator
                + this.author + separator
                + this.pages + separator
                + this.state;
    }

    public boolean isSameId(int id) {
        return this.id == id;
    }

    public boolean containsInTitle(String string) {
        return this.title.contains(string);
    }

    public boolean isRentable() {
        if (this.state == BookState.RENTED) {
            throw new IllegalStateException(ErrorMessages.BOOK_ALREADY_RENTED.getMessage());
        } else if (this.state == BookState.ORGANIZING) {
            throw new IllegalStateException(ErrorMessages.BOOK_BEING_ORGANIZED.getMessage());
        } else if (this.state == BookState.LOST) {
            throw new IllegalStateException(ErrorMessages.BOOK_NOW_LOST.getMessage());
        }
        return this.state == BookState.AVAILABLE;
    }

    public boolean isReturnable() {
        if (this.state == BookState.AVAILABLE) {
            throw new IllegalStateException(ErrorMessages.BOOK_ALREADY_AVAILABLE.getMessage());
        } else if (this.state == BookState.ORGANIZING) {
            throw new IllegalStateException(ErrorMessages.BOOK_BEING_ORGANIZED.getMessage());
        }
        return this.state == BookState.RENTED || this.state == BookState.LOST;
    }

    public boolean isReportableAsLost() {
        if (this.state == BookState.LOST) {
            throw new IllegalStateException(ErrorMessages.BOOK_ALREADY_LOST.getMessage());
        }
        return this.state == BookState.RENTED || this.state == BookState.AVAILABLE || this.state == BookState.ORGANIZING;
    }
}
