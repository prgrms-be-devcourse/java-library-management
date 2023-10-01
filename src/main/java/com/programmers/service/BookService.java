package com.programmers.service;

import com.programmers.exception.ErrorChecking;
import com.programmers.domain.Book;
import com.programmers.exception.ErrorCode;
import com.programmers.exception.LibraryException;
import com.programmers.repository.BookRepository;

import java.util.List;

public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void enrollBook(Book book){
        bookRepository.saveBook(book);
    }

    public List<Book> findAllBooks(){
        return  bookRepository.findAll();
    }

    public List<Book> findBookByTitle(String title){
        return bookRepository.findByBookTitle(title);
    }

    public void rentBook(String bookId) throws LibraryException {
        Book book = getBook(bookId);
        if(!book.checkRentStatus()) throw new LibraryException(ErrorCode.BOOK_CANNOT_RENTED);
        book.rentBook();
    }

    public void returnBook(String bookId) throws LibraryException{
        Book book = getBook(bookId);
        if(!book.checkOngoing()) throw new LibraryException(ErrorCode.BOOK_CANNOT_RETURN);
        book.returnBook();
    }

    public void loseBook(String bookId) throws LibraryException{
        Book book = getBook(bookId);
        if(book.checkingAlreadyLose()) throw new LibraryException(ErrorCode.ALREADY_LOST);
        book.loseBook();
    }

    public void deleteBook(String bookId)throws LibraryException{
        Book book = getBook(bookId);
        if(book.checkingAlreadyDeleted()) throw new LibraryException(ErrorCode.ALREADY_DELETED);
        book.deleteBook();
    }

    private Book getBook(String bookId) throws LibraryException{
        ErrorChecking.checkNumber(bookId);
        return bookRepository.findByBookId(Long.valueOf(bookId))
                .orElseThrow(() -> new LibraryException(ErrorCode.BOOK_NOT_FOUND));
    }
}
