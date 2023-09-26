package com.dev_course.book;

public class Book {
    private int id;
    private String title;
    private String author;
    private int pages;
    private BookState state;

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
