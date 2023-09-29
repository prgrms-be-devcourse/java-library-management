package com.dev_course.book;

import static com.dev_course.book.BookState.AVAILABLE;

public class Book {
    private final int id;
    private final String title;
    private final String author;
    private final int pages;
    private BookState state;
    private long updateAt;

    public Book(int id, String title, String author, int pages, long time) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.state = AVAILABLE;
        this.updateAt = time;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public BookState getState() {
        return state;
    }

    public void setState(BookState state) {
        this.state = state;
    }

    public long getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(long time) {
        this.updateAt = time;
    }

    @Override
    public String toString() {
        return String.format("""
                도서번호 : %d
                제목 : %s
                작가 이름 : %s
                페이지 수 : %s 페이지
                상태 : %s
                ------------------------------
                """, id, title, author, pages, state.label());
    }
}
