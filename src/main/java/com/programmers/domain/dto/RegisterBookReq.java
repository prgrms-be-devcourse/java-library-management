package com.programmers.domain.dto;

import com.programmers.domain.entity.Book;
import com.programmers.domain.enums.BookStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class RegisterBookReq {

    private final String title;
    private final String author;
    private final int pages;

    @Builder
    private RegisterBookReq(String title, String author, int pages) {
        this.title = title;
        this.author = author;
        this.pages = pages;
    }

    public static RegisterBookReq from(String title, String author, int pages) {
        return RegisterBookReq.builder()
            .title(title)
            .author(author)
            .pages(pages)
            .build();
    }

    public Book toBook() {
        return Book.builder()
            .title(title)
            .author(author)
            .pages(pages)
            .status(BookStatus.AVAILABLE)
            .build();
    }
}
