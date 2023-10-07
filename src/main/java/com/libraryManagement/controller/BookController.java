package com.libraryManagement.controller;

import com.libraryManagement.domain.Book;
import com.libraryManagement.util.BookRequestDTO;
import com.libraryManagement.util.BookResponseDTO;
import com.libraryManagement.service.BookService;
import com.libraryManagement.io.BookIO;

import java.io.IOException;
import java.util.List;

public class BookController {
    private final BookService bookService;
    private final BookIO bookIO;

    public BookController(BookService bookService, BookIO bookIO) {
        this.bookService = bookService;
        this.bookIO = bookIO;
    }

    // service 로 create 를 위임, dto 의 패키지 이동 고려
    public void insertBook() throws IOException {
        BookRequestDTO bookRequestDTO = bookIO.inputBookInsert();
        BookResponseDTO bookResponseDTO = new BookResponseDTO(bookRequestDTO);
        bookService.insertBook(bookResponseDTO);
    }

    public void findBooks() {
        List<Book> bookList = bookService.findBooks();
        bookIO.outputBookList(bookList);
    }

    public void findBookByTitle() throws IOException {
        String str = bookIO.inputBookTitleFind();
        List<Book> bookList = bookService.findBooksByTitle(str);
        bookIO.outputBookList(bookList);
    }

    // service 에서 throw 로 처리하도록
    public void rentBook() throws IOException {
        long id = bookIO.inputRentBookId();
        bookService.updateBookStatus();
    }

    public void returnBook() throws IOException {
        long id = bookIO.inputReturnBookId();
        bookService.updateBookStatus();
    }

    public void lostBook() throws IOException {
        long id = bookIO.inputLostBookId();
        bookService.updateBookStatus();
    }

    public void deleteBook() throws IOException {
        long id = bookIO.inputDeleteBookId();
        bookService.updateBookStatus();
    }

    //    public void updateBookStatus(String updateType) throws IOException, InterruptedException {
//
//        long id = bookIO.inputApplyBookId(updateType);
//        Boolean isPossible = bookService.isPossibleUpdateBookStatus(updateType, id);
//        String bookStatus = bookService.updateBookStatus(updateType, id);
//
//        bookIO.outputUpdateMsg(updateType, isPossible, bookStatus);
//    }
}