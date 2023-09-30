package com.example.library.domain;

import com.example.library.convert.Converter;
import lombok.Getter;
import org.json.simple.JSONObject;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
public class Book {
    private long id;
    private String title;
    private String writer;
    private String pageNumber;
    private BookStatusType bookStatusType;

    private LocalDateTime bookReturnTime;

    private final static int ORGANAZING_TIME = 300;

    public Book(long id, String title, String writer, String pageNumber, BookStatusType bookStatusType, LocalDateTime bookReturnTime) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.pageNumber = pageNumber;
        this.bookStatusType = bookStatusType;
        this.bookReturnTime = bookReturnTime;
    }

    public void printBook() {
        System.out.println("도서번호 : " + this.id + "\n" +
                "제목 : " + this.title + "\n" +
                "작가 이름 : " + this.writer + "\n" +
                "페이지 수 : " + this.pageNumber + "\n" +
                "상태 : " + this.bookStatusType + "\n\n" +
                "------------------------------\n");
    }

    public boolean isSame(String bookName) {
        if (this.title.contains(bookName)) {
            return true;
        } else
            return false;
    }

    public void setId(long number) {
        this.id = number;
    }


    public boolean equalsBook(int bookId) {

        if (this.id == bookId) {
            return true;
        }
        return false;
    }
    public void borrowBook() {
        if (this.bookStatusType.borrowBook())
            this.bookStatusType = BookStatusType.대여중;
    }


    public void returnBook() {
        if (this.bookStatusType.returnBook()) {
            this.bookStatusType = BookStatusType.도서정리중;
            this.bookReturnTime = LocalDateTime.now();
        }
    }

    public void loseBook() {
        if (this.bookStatusType.loseBook()) {
            this.bookStatusType = BookStatusType.분실됨;
        }
    }

    public boolean isExceededfiveMinute() {
        if (this.bookStatusType == BookStatusType.도서정리중) {
            Duration duration = Duration.between(this.bookReturnTime, LocalDateTime.now());

            if (duration.getSeconds() >= 300) {
                this.bookStatusType = BookStatusType.대여가능;
                return true;
            }
        }
        return false;
    }

}
