package com.programmers.librarymanagement.domain;

import java.time.LocalDateTime;

public class Book {

    private Long id;
    private String title;
    private String author;
    private Long page;
    private Status status;
    private LocalDateTime returnDateTime;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
