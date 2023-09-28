package library.dto;

import library.domain.Book;

public class BookFindResponse {

    long bookNumber;
    String title;
    String author;
    int pageCount;
    String statusDescription;

    public BookFindResponse(Book book) {
        this.bookNumber = book.getBookNumber();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.pageCount = book.getPageCount();
        this.statusDescription = book.getStatus().getDescription();
    }
}
