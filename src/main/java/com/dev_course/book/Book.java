package com.dev_course.book;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;

import static com.dev_course.book.BookState.PROCESSING;

public class Book {
    private int id;
    private String title;
    private String author;
    private int pages;
    private BookState state;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updateAt;

    // for deserialize from object value
    private Book() {
    }

    public Book(int id, String title, String author, int pages, LocalDateTime time) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.state = BookState.AVAILABLE;
        this.updateAt = time;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public int getPages() {
        return pages;
    }

    public BookState getState() {
        return state;
    }

    public void setState(BookState state) {
        this.state = state;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime time) {
        this.updateAt = time;
    }

    public String info() {
        return String.format("""
                도서번호 : %d
                제목 : %s
                작가 이름 : %s
                페이지 수 : %s 페이지
                상태 : %s""", id, title, author, pages, state.label());
    }

    public boolean isSame(int id) {
        return this.id == id;
    }

    public boolean isProcessed(LocalDateTime processedTime) {
        return state == PROCESSING && updateAt.isBefore(processedTime);
    }
}
