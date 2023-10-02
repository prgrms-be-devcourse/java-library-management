package com.libraryManagement.view;

import com.libraryManagement.model.domain.Book;

import java.util.List;

public class BookView {

    public void bookErrorMsg(String msg) {
        switch(msg) {
            case "LOAN" :
                System.out.println("[System] 이미 대여중인 도서입니다.");
                System.out.println();
                break;
            case "ARRANGED" :
                System.out.println("[System] 현재 정리중인 도서입니다.");
                System.out.println();
                break;
            case "LOST" :
                System.out.println("[System] 현재 분실된 도서입니다.");
                System.out.println();
                break;
            default :
                System.out.println("오류를 찾을수 없음");
        }
    }

    public void bookSelectView(List<Book> bookList) {

        System.out.println("[System] 전체 도서 목록입니다.");
        System.out.println();
        for(Book book : bookList) {
            System.out.println(book);
        }
        System.out.println();
        System.out.println("------------------------------");
    }

    public void bookSelectId(Book b) {
        System.out.println("=== 도서 조회 ===");
        System.out.println(b);
    }


}