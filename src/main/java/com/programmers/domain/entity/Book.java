package com.programmers.domain.entity;

import com.programmers.domain.enums.BookStatus;
import com.programmers.util.UUIDGenerator;
import lombok.Getter;

@Getter
public class Book {
    private final String id = UUIDGenerator.generateUUID();
    private String title;
    private String author;
    private BookStatus status;
    private Integer pages;
    private boolean available;
    private boolean lost;
}
