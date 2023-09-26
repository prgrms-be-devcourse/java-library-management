package com.programmers.domain;

import com.programmers.common.Messages;

import java.util.Scanner;

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
    private BookState state;

    @Override
    public String toString() {
        return "도서번호 : " + id + '\n' +
                "제목 : " + title + '\n' +
                "작가 이름 : " + author + '\n' +
                "페이지 수" + pages + '\n' +
                "상태 : " + state + "\n\n" +
                "------------------------------\n";
    }

    public Book() {
        Scanner scanner = new Scanner(System.in);
        this.id = 1;
        System.out.print("\nQ. 등록할 도서 제목을 입력하세요.\n\n> ");
        this.title = scanner.nextLine();
        System.out.print("\nQ. 작가 이름을 입력하세요.\n\n> ");
        this.author = scanner.nextLine();
        System.out.print("\nQ. 페이지 수를 입력하세요.\n\n> ");
        this.pages = scanner.nextInt();
        this.state = BookState.AVAILABLE;
    }

    public Book(int id, String title, String author, int pages, BookState state) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public boolean isRentable() {
        switch (this.state) {
            case AVAILABLE:
                System.out.println(Messages.BOOK_RENT_SUCCESS);
                return true;
            case RENTED:
                System.out.println(Messages.BOOK_ALREADY_RENTED);
                break;
            case ORGANIZING:
                System.out.println(Messages.BOOK_BEING_ORGANIZED);
                break;
            case LOST:
                System.out.println(Messages.BOOK_NOW_LOST);
                break;
        }
        return false;
    }

    public boolean isReturnable() {
        switch (this.state) {
            case RENTED, LOST:
                System.out.println(Messages.BOOK_RETURN_SUCCESS);
                return true;
            case AVAILABLE:
                System.out.println(Messages.BOOK_RETURN_FAILED);
                break;
            case ORGANIZING:
                System.out.println(Messages.BOOK_BEING_ORGANIZED);
                break;
        }
        return false;
    }

    public boolean isReportableAsLost() {
        switch (this.state) {
            case RENTED, AVAILABLE, ORGANIZING:
                System.out.println(Messages.BOOK_LOST_SUCCESS);
                return true;
            case LOST:
                System.out.println(Messages.BOOK_LOST_FAILED);
                break;
        }
        return false;
    }
}
