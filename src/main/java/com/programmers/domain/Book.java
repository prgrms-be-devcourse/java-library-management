package com.programmers.domain;

import com.programmers.common.ErrorMessages;
import com.programmers.provider.BookIdProvider;

import java.util.NoSuchElementException;
import java.util.Objects;

public class Book {
    private static final int ID_INDEX = 0;
    private static final int TITLE_INDEX = 1;
    private static final int AUTHOR_INDEX = 2;
    private static final int PAGES_INDEX = 3;
    private static final int STATE_INDEX = 4;
    private static final int INFO_COUNT = 5;

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

    public Book(String[] bookInfo) {
        if (bookInfo == null || bookInfo.length < INFO_COUNT) {
            throw new NoSuchElementException(ErrorMessages.CSV_FORMAT_ERROR.toString());
        }
        this.id = parseId(bookInfo[ID_INDEX]);
        this.title = bookInfo[TITLE_INDEX];
        this.author = bookInfo[AUTHOR_INDEX];
        this.pages = parsePages(bookInfo[PAGES_INDEX]);
        this.state = parseState(bookInfo[STATE_INDEX]);
        if (this.state == BookState.ORGANIZING) {
            this.state = BookState.AVAILABLE;
        }
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

    private int parseId(String idStr) {
        try {
            return Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessages.CSV_FORMAT_ERROR.toString());
        }
    }

    private int parsePages(String pagesStr) {
        try {
            return Integer.parseInt(pagesStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessages.CSV_FORMAT_ERROR.toString());
        }
    }

    private BookState parseState(String stateStr) {
        try {
            return BookState.valueOf(stateStr);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(ErrorMessages.CSV_FORMAT_ERROR.toString());
        }
    }
}
