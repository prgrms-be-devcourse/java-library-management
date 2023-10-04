package com.programmers.library.dto;

import com.programmers.library.domain.BookStatus;

import java.time.LocalDateTime;

public class JsonBookDto {
    private int id;
    private String name;
    private String author;
    private int pageCount;
    private BookStatus status;
    private LocalDateTime returnedAt;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public int getPageCount() {
        return pageCount;
    }

    public BookStatus getStatus() {
        return status;
    }

    public LocalDateTime getReturnedAt() {
        return returnedAt;
    }
}
