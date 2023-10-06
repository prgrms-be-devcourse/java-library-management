package model;

import constant.ExceptionMsg;

import java.util.Objects;

public class Book {
    private final Long bookNo;
    private final String title;
    private final String author;
    private final int pageNum;
    private Status status;

    public Book(Long bookNo, String title, String author, int pageNum, Status status) {
        if (title == null || title.isEmpty() || author == null || author.isEmpty() || pageNum < 1) {
            throw new IllegalArgumentException("제목, 작가, 페이지 수를 빈 값 없이 입력해주세요");
        }
        this.bookNo = bookNo;
        this.title = title;
        this.author = author;
        this.pageNum = pageNum;
        this.status = status;
    }

    public boolean isTitleContaining(String searchTitle) {
        return this.title.contains(searchTitle);
    }

    public boolean isBookNo(Long searchBookNo) {
        return Objects.equals(this.bookNo, searchBookNo);
    }

    public boolean isAvailableToBorrow() {
        switch (this.status) {
            case BORROWED -> throw new IllegalStateException(ExceptionMsg.ALREADY_BORROWED.getMessage());
            case LOST -> throw new IllegalStateException(ExceptionMsg.IS_LOST.getMessage());
            case ORGANIZING -> throw new IllegalStateException(ExceptionMsg.IS_ORGANIZING.getMessage());
            case AVAILABLE -> {
                return true;
            }
        }
        return false;
    }

    public boolean isAvailableToReturn() {
        if (this.status.equals(Status.AVAILABLE) || this.status.equals(Status.ORGANIZING)) {
            throw new IllegalStateException(ExceptionMsg.RETURN_FAIL.getMessage());
        }
        return true;
    }

    public boolean isAvailableToChangeLost() {
        if (this.status.equals(Status.LOST)) {
            throw new IllegalStateException(ExceptionMsg.ALREADY_LOST.getMessage());
        }
        return true;
    }

    public void toBorrowed() {
        this.status = Status.BORROWED;
    }

    public void toOrganizing() {
        this.status = Status.ORGANIZING;
    }

    public void toLost() {
        this.status = Status.LOST;
    }

    public void toAvailable() {
        this.status = Status.AVAILABLE;
    }

    @Override
    public String toString() {
        return "\n도서 번호 : " + bookNo +
                "\n제목 : " + title +
                "\n작가 이름 : " + author +
                "\n페이지 수 : " + pageNum +
                "\n상태 : " + status.getStatus() +
                "\n------------------------------";
    }

    public Long getBookNo() {
        return bookNo;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPageNum() {
        return pageNum;
    }

    public Status getStatus() {
        return status;
    }
}
