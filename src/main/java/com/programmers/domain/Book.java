package com.programmers.domain;

import com.programmers.common.ErrorMessages;
import com.programmers.provider.BookIdProvider;

import java.util.Objects;
import java.util.Scanner;

public class Book {

    private final int id;
    private final String title;
    private final String author;
    private final int pages;
    private BookState state;

    @Override
    public String toString() {
        String NEWLINE = System.lineSeparator();
        return NEWLINE +
                "도서번호 : " + id + NEWLINE +
                "제목 : " + title + NEWLINE +
                "작가 이름 : " + author + NEWLINE +
                "페이지 수 : " + pages + NEWLINE +
                "상태 : " + state.getMessage() + NEWLINE + NEWLINE +
                "------------------------------"
                + NEWLINE;
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

    //
    public Book() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nQ. 등록할 도서 제목을 입력하세요.\n\n> ");
        this.title = scanner.nextLine();
        System.out.print("\nQ. 작가 이름을 입력하세요.\n\n> ");
        this.author = scanner.nextLine();
        System.out.print("\nQ. 페이지 수를 입력하세요.\n\n> ");
        this.pages = scanner.nextInt();
        this.state = BookState.AVAILABLE;
        this.id = BookIdProvider.generateBookId();
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

    public Book(int id, String title, String author, int pages, BookState state) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPages() {
        return pages;
    }

    public BookState getState() {
        return state;
    }

    public void setState(BookState state) {
        this.state = state;
    }

    public boolean isRentable() {
        switch (this.state) {
            case AVAILABLE -> {
                return true;
            }
            case RENTED -> throw new RuntimeException(ErrorMessages.BOOK_ALREADY_RENTED.getMessage());
            case ORGANIZING -> throw new RuntimeException(ErrorMessages.BOOK_BEING_ORGANIZED.getMessage());
            case LOST -> throw new RuntimeException(ErrorMessages.BOOK_NOW_LOST.getMessage());
        }
        return false;
    }

    public boolean isReturnable() {
        switch (this.state) {
            case RENTED, LOST -> {
                return true;
            }
            case AVAILABLE -> throw new RuntimeException(ErrorMessages.BOOK_ALREADY_AVAILABLE.getMessage());
            case ORGANIZING -> throw new RuntimeException(ErrorMessages.BOOK_BEING_ORGANIZED.getMessage());
        }
        return false;
    }

    public boolean isReportableAsLost() {
        switch (this.state) {
            case RENTED, AVAILABLE, ORGANIZING -> {
                return true;
            }
            case LOST -> throw new RuntimeException(ErrorMessages.BOOK_ALREADY_LOST.getMessage());
        }
        return false;
    }
}
