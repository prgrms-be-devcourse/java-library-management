package com.programmers.library_management.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Book {
    private static int LastBookNumber = 0;
    private final int bookNumber;
    private final String title;
    private final String writer;
    private final int pageNumber;
    private Status status;
    private LocalDateTime returnDateTime;


    /**
     * Book 생성자
     * 도서 관리 업무중 도서를 추가할 때 사용하는 생성자입니다.
     *
     * @param title 책 제목
     * @param writer 작가
     * @param pageNumber 책 쪽수
     */
    public Book(String title, String writer, String pageNumber) {
        this.bookNumber = ++LastBookNumber;
        this.title = title;
        this.writer = writer;
        this.pageNumber = Integer.parseInt(pageNumber);
    }

    /**
     * Book 생성자
     * CSV File 에서 책을 추가할 때 사용하는 생성자입니다.
     *
     * @param bookNumber 책 번호
     * @param title 책 제목
     * @param writer 작가
     * @param pageNumber 책 쪽수
     * @param status 도서 대여 상태
     * @param returnDateTime 최근 반납 일자
     */
    public Book(String bookNumber, String title, String writer, String pageNumber, String status, String returnDateTime) {
        this.bookNumber = Integer.parseInt(bookNumber);
        LastBookNumber = this.bookNumber;
        this.title = title;
        this.writer = writer;
        this.pageNumber = Integer.parseInt(pageNumber);
        this.status = Status.valueOf(status);
        this.returnDateTime = returnDateTime.equals("null") ? null : LocalDateTime.parse(returnDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public String toString() {
        return "도서번호: " + bookNumber + '\n'+
                "제목: '" + title + '\n' +
                "작가 이름: " + writer + '\n' +
                "페이지 수: " + pageNumber + " 페이지\n" +
                "상태: " + status.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return pageNumber == book.pageNumber && Objects.equals(title, book.title) && Objects.equals(writer, book.writer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, writer, pageNumber);
    }
}
