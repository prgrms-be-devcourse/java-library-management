package com.programmers.domain.entity;

import com.programmers.config.DependencyInjector;
import com.programmers.domain.enums.BookStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Book {

    private Long id;
    private String title;
    private String author;
    private BookStatus status;
    private Integer pages;

    @Builder
    private Book(Long id,String title, String author, BookStatus status, Integer pages) {
        this.id = DependencyInjector.getInstance().getIdGenerator().generateId();
        this.title = title;
        this.author = author;
        this.status = status;
        this.pages = pages;
    }

    public void updateBookStatusToRent() {
        validateCanBeRented();
        this.status = BookStatus.RENTED;
    }

    public void updateBookStatusToOrganizing() {
        validateCanBeOrganizing();
        this.status = BookStatus.ORGANIZING;
    }


    public void updateBookStatusToAvailable() {
        validateCanBeAvailable();
        this.status = BookStatus.AVAILABLE;
    }

    private void validateCanBeRented() {
        BookStatus.checkIfRentable(this.status);
    }

    private void validateCanBeAvailable() {
        BookStatus.checkIfAvailable(this.status);
    }

    private void validateCanBeOrganizing() {
        BookStatus.checkIfOrganizable(this.status);
    }

}
