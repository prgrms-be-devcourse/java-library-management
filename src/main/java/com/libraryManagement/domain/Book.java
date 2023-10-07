package com.libraryManagement.domain;

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

    public enum Status {

        AVAILABLE("대여가능"),
        RENT("대여중"),
        READY("준비중"),
        LOST("분실됨"),
        DELETE("삭제됨");

        final private String name;

        public String getName() {
            return name;
        }

        Status(String name) {
            this.name = name;
        }
    }
}
