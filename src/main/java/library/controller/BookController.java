package library.controller;

import library.dto.BookFindResponse;
import library.dto.BookSaveRequest;
import library.service.BookService;

import java.util.List;

public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    public void addBook(BookSaveRequest bookSaveRequest) {
        bookService.addBook(bookSaveRequest);
    }

    public List<BookFindResponse> findAllBooks() {
        return bookService.findAllBooks();
    }

    public List<BookFindResponse> findBookLisContainTitle(String searchTitle) {
        return bookService.findBookListContainTitle(searchTitle);
    }

    public void rentBook(long rentBookNumber) {
        bookService.rentBook(rentBookNumber);
    }

    public void returnBook(long returnBookNumber) {
        bookService.returnBook(returnBookNumber);
    }

    public void lostBook(long lostBookNumber) {
        bookService.lostBook(lostBookNumber);
    }

    public void deleteBook(long deleteBookNumber) {
        bookService.deleteBook(deleteBookNumber);
    }
}
