package com.libraryManagement.model.domain;

public class Book {
    private final long id;
    private final String title;
    private final String author;
    private final int pages;

    /*
        Builder pattern의 장점

        - 각 인자의 의미를 알기 쉽다.
        - setter 메소드가 없으므로 변경 불가능(immutable) 객체를 만들 수 있다.
        - 한 번에 객체를 생성하므로 객체 일관성(consistency)이 깨지지 않는다.
        - build() 메소드에서 잘못된 값이 입력되었는지 검증할 수 있다.
     */
    public static class Builder {
        private long id;
        private String title;
        private String author;
        private int pages;

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

        public Book build() {
            return new Book(this);
        }
    }

    public Book(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.author = builder.author;
        this.pages = builder.pages;
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
}
