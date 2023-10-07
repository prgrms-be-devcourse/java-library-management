package com.programmers.domain.entity;

import com.programmers.config.DependencyInjector;
import com.programmers.domain.enums.Action;
import com.programmers.domain.status.Available;
import com.programmers.domain.status.BookStatus;
import com.programmers.domain.status.Lost;
import com.programmers.domain.status.Organizing;
import com.programmers.domain.status.Rented;
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
    private Book(Long id, String title, String author, BookStatus status, Integer pages) {
        this.id = DependencyInjector.getInstance().getIdGenerator().generateId();
        this.title = title;
        this.author = author;
        this.status = status;
        this.pages = pages;
    }

    public void updateBookStatusToRent() {
        validateCanBeRented();
        this.status = new Rented();
    }

    public void updateBookStatusToOrganizing() {
        validateCanBeOrganizing();
        this.status = new Organizing();
    }

    public void updateBookStatusToLost() {
        validateCanBeLost();
        this.status = new Lost();
    }

    public void updateBookStatusToAvailable() {
        validateCanBeAvailable();
        this.status = new Available();
    }

    private void validateCanBeRented() {
        status.validateAction(Action.RENT);
    }

    private void validateCanBeOrganizing() {
        status.validateAction(Action.RETURN);
    }

    private void validateCanBeLost() {
        status.validateAction(Action.REPORT_LOST);
    }

    private void validateCanBeAvailable() {
        status.validateAction(Action.SET_TO_AVAILABLE);
    }
}
