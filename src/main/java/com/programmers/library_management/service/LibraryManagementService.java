package com.programmers.library_management.service;

import com.programmers.library_management.domain.Book;
import com.programmers.library_management.domain.StatusType;
import com.programmers.library_management.exception.*;
import com.programmers.library_management.repository.BookRepository;

import java.util.List;

public class LibraryManagementService {
    private final BookRepository bookRepository;

    public LibraryManagementService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void addBook(String title, String writer, int pageNumber) {
        Book newBook = Book.of(bookRepository.generateBookId(), title, writer, pageNumber);
        for (Book book : bookRepository.findByTitle(title)) {
            if (book.equals(newBook)) {
                throw new CBookAlreadyExistException();
            }
        }
        bookRepository.save(newBook);
    }

    public void rantBook(int id) {
        Book book = bookRepository.findById(id).orElseThrow(CBookIdNotExistException::new);
        switch (book.getStatus()) {
            case Ranted -> throw new CBookAlreadyRantedException();
            case Lost -> throw new CBookAlreadyLostException();
            case Organize -> {
                if (!book.isOrganized()) {
                    throw new CBookInOrganizeException();
                }
            }
        }
        book.rant();
        bookRepository.save(book);
    }

    public void returnBook(int id) {
        Book book = bookRepository.findById(id).orElseThrow(CBookIdNotExistException::new);
        switch (book.getStatus()) {
            case Organize, Available -> throw new CBookAlreadyReturnedException();
        }
        book.returned();
        bookRepository.save(book);
    }

    public void lostBook(int id) {
        Book book = bookRepository.findById(id).orElseThrow(CBookIdNotExistException::new);
        if (book.getStatus().equals(StatusType.Lost)) {
            throw new CBookAlreadyLostException();
        }
        book.lost();
        bookRepository.save(book);
    }

    public void deleteBook(int id) {
        Book book = bookRepository.findById(id).orElseThrow(CBookIdNotExistException::new);
        bookRepository.delete(book);
    }

    public List<Book> showAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> searchBook(String text) {
        return bookRepository.findByTitle(text);
    }

    public void updateBookStatus(){
        bookRepository.updateAllBookStatus();
    }

}
