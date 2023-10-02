package org.example.domain;

public class Book {
    private Integer id;
    private String title;
    private String author;
    private Integer pageSize;
    private BookStatusType status;

    public Book(Integer id, String title, String author, Integer pageSize) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.pageSize = pageSize;
        this.status = BookStatusType.BORROW_AVAILABE;
    }

    public Book(Integer id, String title, String author, Integer pageSize, BookStatusType status) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.pageSize = pageSize;
        this.status = status;
    }

    @Override
    public String toString() {
        return "도서번호: " + id + "\n" +
                "제목: " + title + "\n" +
                "작가 이름: " + author + "\n" +
                "페이지 수: " + pageSize + "페이지" + "\n" +
                "상태: " + status.getName();
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public BookStatusType getStatus() {
        return status;
    }

    public void setStatus(BookStatusType status) {
        this.status = status;
    }

    public boolean canBorrow() {
        return status == BookStatusType.BORROW_AVAILABE;
    }

    public boolean canReturn() {
        return status == BookStatusType.BORROWING || status == BookStatusType.LOST;
    }

    public boolean canLost() {
        return status != BookStatusType.LOST;
    }
}
