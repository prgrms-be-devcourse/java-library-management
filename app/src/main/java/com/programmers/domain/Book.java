package com.programmers.domain;

/*
도서번호(중복되지 않아야합니다. ISBN을 사용하라는 의미가 아닙니다.)
제목
작가 이름
페이지 수
상태
대여 가능
대여중
도서 정리중
분실됨
 */
public class Book {

    private final int id;
    private final String title;
    private final String author;
    private final int pages;
    private final BookState state;

    @Override
    public String toString() {
        return "도서번호 : " + id + '\n' +
                "제목 : " + title + '\n' +
                "작가 이름 : " + author + '\n' +
                "페이지 수" + pages + '\n' +
                "상태 : " + state + "\n\n" +
                "------------------------------\n";
    }

    public Book(int id, String title, String author, int pages, BookState state) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.state = state;
    }
}
