package com.programmers.library_management.service;

import com.programmers.library_management.domain.Book;
import com.programmers.library_management.domain.Status;
import com.programmers.library_management.exception.*;
import com.programmers.library_management.repository.BookRepository;

import java.util.List;

public class LibraryManagementService {
    private final BookRepository bookRepository;

    public LibraryManagementService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void addBook(String title, String writer, int pageNumber){
        Book newBook = new Book(bookRepository.generateBookNumber(), title, writer, pageNumber);
        for(Book book:bookRepository.findByTitle(title)){
            if(book.equals(newBook)){
                throw new CBookAlreadyExistException();
            }
        }
        bookRepository.save(newBook);
    }

    public void rantBook(int bookNumber){
        Book book = bookRepository.findByBookNumber(bookNumber).orElseThrow(CBookNumberNotExistException::new);
        switch (book.getStatus()){
            case Ranted -> throw new CBookAlreadyRantedException();
            case Lost -> throw new CBookAlreadyLostException();
            case Organized -> {
                if (book.isOrganized()){
                    throw new CBookInOrganizeException();
                }
            }
        }
        book.rant();
        bookRepository.save(book);
    }

    public void returnBook(int bookNumber){
        Book book = bookRepository.findByBookNumber(bookNumber).orElseThrow(CBookNumberNotExistException::new);
        switch (book.getStatus()){
            case Organized, Available -> throw new CBookAlreadyReturnedException();
        }
        book.returned();
        bookRepository.save(book);
    }

    public void lostBook(int bookNumber){
        Book book = bookRepository.findByBookNumber(bookNumber).orElseThrow(CBookNumberNotExistException::new);
        if(book.getStatus().equals(Status.Lost)){
            throw new CBookAlreadyLostException();
        }
        book.lost();
        bookRepository.save(book);
    }

    public void deleteBook(int bookNumber){
        Book book = bookRepository.findByBookNumber(bookNumber).orElseThrow(CBookNumberNotExistException::new);
        bookRepository.delete(book);
    }

    public List<Book> showAllBooks(){
        bookRepository.updateAllBookStatus();
        return bookRepository.findAll();
    }

    public List<Book> searchBook(String text){
        bookRepository.updateAllBookStatus();
        return bookRepository.findByTitle(text);
    }

}
