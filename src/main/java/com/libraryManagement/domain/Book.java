package com.libraryManagement.domain;

import static com.libraryManagement.domain.Book.BookStatus.*;

public class Book {
    private final long id;
    private final String title;
    private final String author;
    private final int pages;
    private String status;

    public static class Builder {
        private long id;
        private String title;
        private String author;
        private int pages;
        private String status;

        public Builder id(long id) {
            this.id = id;
            return this;
        }
        public Builder title(String title) {
            this.title = title;
            return this;
        }
        public Builder author(String author) {
            this.author = author;
            return this;
        }
        public Builder pages(int pages) {
            this.pages = pages;
            return this;
        }
        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Book build() {
            return new Book(this);
        }
    }

    public Book(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.author = builder.author;
        this.pages = builder.pages;
        this.status = builder.status;
    }

    public Boolean isPossibleRent() {
        // 대여가능일 때만 대여가능
        if(this.status.equals(AVAILABLE.getName())){
            return true;
        }
        return false;
    }

    public Boolean isPossibleReturn() {
        // 대여가능일 때만 반납 불가
        if(this.status.equals(AVAILABLE.getName())){
            return false;
        }
        return true;
    }

    public Boolean isPossibleLost() {
        // 분실됨일 때만 분실처리 불가
        if(this.status.equals(LOST.getName())){
            return false;
        }
        return true;
    }

    public Boolean isPossibleDelete() {
        // 존재하지 않는 도서일 때만 삭제처리 불가
        if(this.status.equals(DELETE.getName())){
            return false;
        }
        return true;
    }

    public long getId() {
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
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "도서번호 : " + id +
                "\n제목 : " + title +
                "\n작가 이름 : " + author +
                "\n페이지 수 : " + pages + " 페이지" +
                "\n상태 : " + status + "\n";
    }

    public enum BookStatus {

        AVAILABLE("대여가능"),
        RENT("대여중"),
        ORGANIZING("정리중"),
        LOST("분실됨"),
        DELETE("삭제됨");

        final private String name;

        public String getName() {
            return name;
        }

        BookStatus(String name) {
            this.name = name;
        }
    }
}
