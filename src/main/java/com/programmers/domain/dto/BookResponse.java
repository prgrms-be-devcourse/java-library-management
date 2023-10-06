package com.programmers.domain.dto;

import com.programmers.domain.entity.Book;
import com.programmers.domain.enums.BookStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BookResponse {

    private final Long id;
    private final String title;
    private final String author;
    private final Integer pages;
    private final BookStatus status;

    @Builder
    public BookResponse(Long id, String title, String author, Integer pages, BookStatus status) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format(
            "도서 번호: %d%n" +
                "제목: %s%n" +
                "작가: %s%n" +
                "페이지 수: %d%n" +
                "상태: %b%n%n" +
                "------------------------------%n",
            id, title, author, pages, status
        );
    }

    public static BookResponse of(Book book) {
        return BookResponse.builder()
            .id(book.getId())
            .title(book.getTitle())
            .author(book.getAuthor())
            .pages(book.getPages())
            .status(book.getStatus())
            .build();
    }
}
