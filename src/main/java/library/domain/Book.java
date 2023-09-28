package library.domain;

import java.time.LocalDateTime;

import static library.domain.BookStatus.AVAILABLE;

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
}
