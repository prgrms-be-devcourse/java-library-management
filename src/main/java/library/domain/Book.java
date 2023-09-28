package library.domain;

import library.exception.BookException;

import java.time.LocalDateTime;
import java.util.Objects;

import static library.domain.BookStatus.*;
import static library.exception.BookErrorMessage.*;

public class Book {
    private static final int CLEANUP_MINUTES = 5;


    private final long bookNumber;
    private final String title;
    private final String author;
    private final int pageCount;
    private LocalDateTime returnDateTime;
    private BookStatus status;

    public Book(long bookNumber, String title, String author, int pageCount) {
        this.bookNumber = bookNumber;
        this.title = title;
        this.author = author;
        this.pageCount = pageCount;
        this.status = AVAILABLE;
    }

    public Book(long bookNumber, String title, String author, int pageCount, LocalDateTime returnDateTime, BookStatus status) {
        this.bookNumber = bookNumber;
        this.title = title;
        this.author = author;
        this.pageCount = pageCount;
        this.returnDateTime = returnDateTime;
        this.status = status;
    }

    public long getBookNumber() {
        return bookNumber;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPageCount() {
        return pageCount;
    }

    public BookStatus getStatus() {
        updateCleanUpStatus();
        return status;
    }

    public LocalDateTime getReturnDateTime() {
        return returnDateTime;
    }

    public boolean equalsBookNumber(long bookNumber) {
        return this.bookNumber == bookNumber;
    }

    public boolean containsTitle(String title) {
        return this.title.contains(title);
    }

    public boolean returnDateTimeIsNull() {
        return this.returnDateTime == null;
    }

    public void toRent() {
        updateCleanUpStatus();
        switch (this.status) {
            case AVAILABLE -> this.status = RENTED;
            case IN_CLEANUP -> throw new BookException(BOOK_IN_CLEANUP);
            case LOST -> throw new BookException(BOOK_LOST);
            case RENTED -> throw new BookException(BOOK_ALREADY_RENTED);
        }
    }

    public void toReturn() {
        switch (this.status) {
            case RENTED, LOST -> this.status = IN_CLEANUP;
            case IN_CLEANUP -> throw new BookException(BOOK_IN_CLEANUP);
            case AVAILABLE -> throw new BookException(BOOK_ALREADY_AVAILABLE);
        }
        this.returnDateTime = LocalDateTime.now();
    }

    public void toLost() {
        switch (this.status) {
            case RENTED, IN_CLEANUP, AVAILABLE -> this.status = LOST;
            case LOST -> throw new BookException(BOOK_ALREADY_LOST);
        }
    }

    private void updateCleanUpStatus() {
        if (this.status == IN_CLEANUP
                && LocalDateTime.now().isAfter(this.returnDateTime.plusMinutes(CLEANUP_MINUTES))) {
            this.status = AVAILABLE;
            this.returnDateTime = null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return getBookNumber() == book.getBookNumber();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBookNumber());
    }
}
