package com.programmers.app.book.domain;

public class Book {
    private int bookNumber;
    private String title;
    private String author;
    private int totalPages;
    private BookStatus status;

    public Book(int bookNumber, String title, String author, int totalPages) {
        this.bookNumber = bookNumber;
        this.title = title;
        this.author = author;
        this.totalPages = totalPages;
        this.status = BookStatus.IN_PLACE;
    }

    public int getBookNumber() {
        return bookNumber;
    }

    public boolean containsInTitle(String title) {
        return this.title.contains(title);
    }

    public boolean isInPlace() {
        return status.equals(BookStatus.IN_PLACE);
    }

    public boolean isOnLoan() {
        return status.equals(BookStatus.ON_LOAN);
    }

    public boolean isLost() {
        return status.equals(BookStatus.LOST);
    }

    public boolean isOnArrangement() {
        return status.equals(BookStatus.ON_ARRANGEMENT);
    }

    public void setStatus(BookStatus bookStatus) {
        this.status = bookStatus;
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
