package com.programmers.app.book.domain;

public class Book {
    private long bookNumber;
    private String title;
    private String author;
    private int totalPages;
    private BookStatus status;

    public Book(long bookNumber, String title, String author, int totalPages) {
        this.bookNumber = bookNumber;
        this.title = title;
        this.author = author;
        this.totalPages = totalPages;
        this.status = BookStatus.AVAILABLE;
    }

    public long getBookNumber() {
        return bookNumber;
    }

    public boolean isMatching(String title) {
        return this.title.contains(title);
    }

    public BookStatus getStatus() {
        return this.status;
    }

    @Override
    public String toString() {
        return "도서 번호 : " + bookNumber + "\n" +
                "제목 : " + title + '\n' +
                "작가 이름 : " + author + '\n' +
                "페이지 수 : " + totalPages + "\n" +
                "상태 : " + status.getStatus() + "\n" +
                "------------------------------";
    }
}
