package library.service;

import library.domain.Book;
import library.dto.BookFindResponse;
import library.dto.BookSaveRequest;
import library.exception.BookException;
import library.repository.BookRepository;

import java.util.List;

import static library.exception.BookErrorMessage.BOOK_ALREADY_EXISTS;
import static library.exception.BookErrorMessage.BOOK_NOT_FOUND;

public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void addBook(BookSaveRequest bookSaveRequest) {
        long bookNumber = bookRepository.getNextBookNumber();
        validateBookNumber(bookNumber);
        bookRepository.add(bookSaveRequest.toBook(bookNumber));
    }

    private void validateBookNumber(long bookNumber) {
        bookRepository
                .findByBookNumber(bookNumber)
                .ifPresent(book -> {
                    throw new BookException(BOOK_ALREADY_EXISTS);
                });
    }

    public List<BookFindResponse> findAllBooks() {
        return bookRepository
                .findAll()
                .stream()
                .map(BookFindResponse::new)
                .toList();
    }

    public List<BookFindResponse> findBookListContainTitle(String title) {
        return bookRepository
                .findListContainTitle(title)
                .stream()
                .map(BookFindResponse::new)
                .toList();
    }

    public void rentBook(long rentBookNumber) {
        findByBookNumber(rentBookNumber).toRent();
    }

    public void returnBook(long returnBookNumber) {
        findByBookNumber(returnBookNumber).toReturn();
    }

    public void lostBook(long lostBookNumber) {
        findByBookNumber(lostBookNumber).toLost();
    }

    public void deleteBook(long deleteBookNumber) {
        bookRepository.delete(findByBookNumber(deleteBookNumber));
    }

    private Book findByBookNumber(long bookNumber) {
        return bookRepository
                .findByBookNumber(bookNumber)
                .orElseThrow(() -> new BookException(BOOK_NOT_FOUND));
    }
}
