package com.example.library.domain;

import com.example.library.convert.Converter;
import org.json.simple.JSONObject;

import java.time.Duration;
import java.time.LocalDateTime;

public class Book {
    private long id;
    private String title;
    private String writer;
    private String pageNumber;
    private BookStatus bookStatus;

    private LocalDateTime bookReturnTime;

    public Book(long id, String title, String writer, String pageNumber, BookStatus bookStatus, LocalDateTime bookReturnTime) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.pageNumber = pageNumber;
        this.bookStatus = bookStatus;
        this.bookReturnTime = bookReturnTime;
    }

    public void printBook() {
        System.out.println("도서번호 : " + this.id + "\n" +
                "제목 : " + this.title + "\n" +
                "작가 이름 : " + this.writer + "\n" +
                "페이지 수 : " + this.pageNumber + "\n" +
                "상태 : " + this.bookStatus + "\n\n" +
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

    public long getId() {
        return this.id;
    }

    public boolean equalsBook(int bookId) {

        if (this.id == bookId) {
            return true;
        }
        return false;
    }
    public void borrowBook() {
        if (this.bookStatus.borrowBook())
            this.bookStatus = BookStatus.대여중;
    }


    public void returnBook() {
        if (this.bookStatus.returnBook()) {
            this.bookStatus = BookStatus.도서정리중;
            this.bookReturnTime = LocalDateTime.now();
        }
    }

    public void loseBook() {
        if (this.bookStatus.loseBook()) {
            this.bookStatus = BookStatus.분실됨;
        }
    }

    public boolean isExceededfiveMinute() {
        if (this.bookStatus == BookStatus.도서정리중) {
            Duration duration = Duration.between(this.bookReturnTime, LocalDateTime.now());

            if (duration.getSeconds() >= 300) {
                this.bookStatus = BookStatus.대여가능;
                return true;
            }
        }
        return false;
    }

    public JSONObject convertJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", Long.toString(this.id));
        jsonObject.put("title", this.title);
        jsonObject.put("writer", this.writer);
        jsonObject.put("pageNumber", this.pageNumber);
        jsonObject.put("bookStatus", this.bookStatus.name());
        jsonObject.put("bookReturnTime", Converter.convertLocalDateTimeToString(this.bookReturnTime));
        return jsonObject;
    }

}
