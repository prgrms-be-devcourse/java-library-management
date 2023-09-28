package library.dto;

import library.domain.Book;

public class BookSaveRequest {
    String title;
    String author;
    int pageCount;

    public BookSaveRequest(String title, String author, int pageCount) {
        this.title = title;
        this.author = author;
        this.pageCount = pageCount;
    }

    public Book toBook(long bookNumber) {
        return new Book(bookNumber, title, author, pageCount);
    }
}
