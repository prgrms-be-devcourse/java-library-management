package com.libraryManagement.controller;

import com.libraryManagement.domain.Book;
import com.libraryManagement.io.BookMenu;
import com.libraryManagement.util.BookRequestDTO;
import com.libraryManagement.util.BookResponseDTO;
import com.libraryManagement.service.BookService;
import com.libraryManagement.io.BookIO;

import java.io.IOException;
import java.util.List;

public class BookController {
    private final BookService bookService;
    private final BookIO bookIO;
    private final BookMenu bookMenu;

    public BookController(BookService bookService, BookIO bookIO, BookMenu bookMenu) {
        this.bookService = bookService;
        this.bookIO = bookIO;
        this.bookMenu = bookMenu;
    }

    public void startBookMenu() throws Exception {

        while(true) {
            int selectedBookMenuNum = bookMenu.startBookMenu();

            switch (selectedBookMenuNum) {
                case 1 :
                    insertBook();
                    break;
                case 2 :
                    findBooks();
                    break;
                case 3 :
                    findBookByTitle();
                    break;
                case 4 :
                    rentBook();
                    break;
                case 5 :
                    returnBook();
                    break;
                case 6 :
                    lostBook();
                    break;
                case 7 :
                    deleteBook();
                    break;
            }
        }
    }

    public void insertBook() throws IOException {
        BookRequestDTO bookRequestDTO = bookIO.inputToInsertBook();
        BookResponseDTO bookResponseDTO = new BookResponseDTO(bookRequestDTO);
        bookService.insertBook(bookResponseDTO);
        bookIO.outputInsertMsg();
    }

    public void findBooks() throws Exception {
        List<Book> bookList = bookService.findBooks();
        bookIO.outputBookList(bookList);
        bookIO.outputFindBooksMsg();
    }

    public void findBookByTitle() throws Exception {
        String str = bookIO.inputBookTitleToFind();
        List<Book> bookList = bookService.findBooksByTitle(str);
        bookIO.outputBookList(bookList);
        bookIO.outputFindBookByTitleMsg();
    }

    // service 에서 throw 로 처리하도록
    public void rentBook() throws Exception {
        long id = bookIO.inputRentBookId();
        bookService.rentBook(id);
        bookIO.outputRentMsg();
    }

    public void returnBook() throws Exception {
        long id = bookIO.inputReturnBookId();
        bookService.returnBook(id);
        bookIO.outputReturnMsg();
    }

    public void lostBook() throws Exception {
        long id = bookIO.inputLostBookId();
        bookService.lostBook(id);
        bookIO.outputLostMsg();
    }

    public void deleteBook() throws Exception {
        long id = bookIO.inputDeleteBookId();
        bookService.deleteBook(id);
        bookIO.outputDeleteMsg();
    }
}