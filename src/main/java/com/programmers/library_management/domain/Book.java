package com.programmers.library_management.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Book {
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
    public Book(int bookNumber, String title, String writer, int pageNumber) {
        this.bookNumber = bookNumber;
        this.title = title;
        this.writer = writer;
        this.pageNumber = pageNumber;
        this.status = Status.Available;
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
    public Book(int bookNumber, String title, String writer, int pageNumber, Status status, String returnDateTime) {
        this.bookNumber = bookNumber;
        this.title = title;
        this.writer = writer;
        this.pageNumber = pageNumber;
        this.status = status;
        this.returnDateTime = returnDateTime.equals("null") ? null : LocalDateTime.parse(returnDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"));
    }

    @Override
    public String toString() {
        return "도서번호: " + bookNumber + '\n'+
                "제목: " + title + '\n' +
                "작가 이름: " + writer + '\n' +
                "페이지 수: " + pageNumber + " 페이지\n" +
                "상태: " + status.getStatusMessage();
    }

    public String toCsvString(){
        return bookNumber+","+title+","+writer+","+pageNumber+","+status+","+returnDateTime+"\n";
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

    public int getBookNumber() {
        return bookNumber;
    }

    public String getTitle() {
        return title;
    }

    public Status getStatus() {
        return status;
    }

    public boolean isOrganized(){
        if (status.equals(Status.Organized)){
            int ORGANIZE_TIME = 5;
            return returnDateTime.plusMinutes(ORGANIZE_TIME).isBefore(LocalDateTime.now());
        }
        return false;
    }

    public void rant(){
        this.status = Status.Ranted;
    }

    public void lost(){
        this.status = Status.Lost;
    }

    public void available(){this.status = Status.Available;}

    public void returned(){
        this.status = Status.Organized;
        this.returnDateTime = LocalDateTime.now();
    }
}
